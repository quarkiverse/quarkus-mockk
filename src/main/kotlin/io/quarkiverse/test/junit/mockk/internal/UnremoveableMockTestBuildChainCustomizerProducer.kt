package io.quarkiverse.test.junit.mockk.internal

import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.arc.deployment.UnremovableBeanBuildItem
import io.quarkus.builder.BuildChainBuilder
import io.quarkus.test.junit.buildchain.TestBuildChainCustomizerProducer
import org.jboss.jandex.DotName
import org.jboss.jandex.Index
import java.util.function.Consumer

class UnremoveableMockTestBuildChainCustomizerProducer: TestBuildChainCustomizerProducer {

    companion object {
        val INJECT_MOCK: DotName = DotName.createSimple(InjectMock::class.qualifiedName)
    }

    override fun produce(testClassesIndex: Index?): Consumer<BuildChainBuilder> = Consumer { buildChainBuilder ->
        buildChainBuilder.addBuildStep { context ->
            val mockTypes = testClassesIndex
                ?.getAnnotations(INJECT_MOCK)
                ?.map { it.target().asField().type().name().toString() }
                ?.toSet()
            mockTypes?.let {
                context.produce(UnremovableBeanBuildItem(UnremovableBeanBuildItem.BeanClassNamesExclusion(mockTypes)))
            }
        }
    }
}