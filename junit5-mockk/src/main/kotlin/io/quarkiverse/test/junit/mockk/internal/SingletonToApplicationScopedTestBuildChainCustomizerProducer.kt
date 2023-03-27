package io.quarkiverse.test.junit.mockk.internal

import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.arc.deployment.AnnotationsTransformerBuildItem
import io.quarkus.arc.processor.AnnotationsTransformer
import io.quarkus.arc.processor.DotNames
import io.quarkus.builder.BuildChainBuilder
import io.quarkus.test.junit.buildchain.TestBuildChainCustomizerProducer
import org.jboss.jandex.AnnotationTarget
import org.jboss.jandex.AnnotationValue
import org.jboss.jandex.ClassInfo
import org.jboss.jandex.DotName
import org.jboss.jandex.Index
import java.util.function.Consumer

class SingletonToApplicationScopedTestBuildChainCustomizerProducer: TestBuildChainCustomizerProducer {

    companion object {
        val INJECT_MOCK: DotName = DotName.createSimple(InjectMock::class.java.name)
    }

    override fun produce(testClassesIndex: Index): Consumer<BuildChainBuilder?> = Consumer { buildChainBuilder ->
        buildChainBuilder?.let { buildChainBuilder.addBuildStep { context ->
            val mockTypes = testClassesIndex.getAnnotations(INJECT_MOCK)
                ?.filter { it.target().kind() == AnnotationTarget.Kind.FIELD }
                ?.filter {
                    val allowScopeConversionValue = it.value("convertScopes")
                    allowScopeConversionValue != null && allowScopeConversionValue.asBoolean()
                }
                ?.map { it.target().asField().type().name() }
                ?.toSet() ?: setOf<AnnotationValue>()

            if (mockTypes.isNotEmpty()) {
                context.produce(AnnotationsTransformerBuildItem(object : AnnotationsTransformer {
                    override fun appliesTo(kind: AnnotationTarget.Kind) = kind == AnnotationTarget.Kind.CLASS
                            || kind == AnnotationTarget.Kind.METHOD

                    override fun transform(transformationContext: AnnotationsTransformer.TransformationContext) {
                        val target = transformationContext.target
                        if (target.kind() == AnnotationTarget.Kind.CLASS) { // scope on bean case
                            val classInfo = target.asClass()
                            if (isMatchingBean(classInfo)) {
                                if (classInfo.declaredAnnotation(DotNames.SINGLETON) != null) {
                                    replaceSingletonWithApplicationScoped(transformationContext)
                                }
                            }
                        } else if (target.kind() == AnnotationTarget.Kind.METHOD) { // CDI producer case
                            val methodInfo = target.asMethod()
                            if (methodInfo.annotation(DotNames.PRODUCES) != null
                                && methodInfo.annotation(DotNames.SINGLETON) != null
                            ) {
                                val returnType = methodInfo.returnType().name()
                                if (mockTypes.contains(returnType)) {
                                    replaceSingletonWithApplicationScoped(transformationContext)
                                }
                            }
                        }
                    }

                    private fun replaceSingletonWithApplicationScoped(transformationContext: AnnotationsTransformer.TransformationContext) {
                        transformationContext.transform()
                            .remove { it.name() == DotNames.SINGLETON }
                            .add(DotNames.APPLICATION_SCOPED)
                            .done()
                    }

                    // this is very simplistic and is the main reason why the annotation transformer strategy
                    // is fine with most cases, but it can't cover all cases
                    private fun isMatchingBean(classInfo: ClassInfo): Boolean {
                        // class type matches
                        if (mockTypes.contains(classInfo.name())) {
                            return true
                        }
                        if (mockTypes.contains(classInfo.superName())) {
                            return true
                        }
                        for (iface in classInfo.interfaceNames()) {
                            if (mockTypes.contains(iface)) {
                                return true
                            }
                        }
                        return false
                    }
                }))
            }
        }
        }?.produces(AnnotationsTransformerBuildItem::class.java)?.build()
    }
}