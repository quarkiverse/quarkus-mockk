package io.quarkiverse.test.junit.mockk.internal

import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkiverse.test.junit.mockk.InjectSpy
import io.quarkus.arc.Arc
import java.lang.IllegalStateException
import java.lang.reflect.Field
import javax.inject.Qualifier

object ReflectionUtils {

    fun getBeanInstance(testInstance: Any, field: Field, annotationType: Class<out Annotation>): Any {
        val fieldClass = field.type
        return Arc.container().instance(fieldClass, *getQualifier(field))?.get() ?: throw IllegalStateException(
            "Invalid use of ${annotationType.typeName} - could not determine bean of type: $fieldClass. Offending field is ${field.name} of test class "
                    + testInstance.javaClass
        )
    }

    internal fun getQualifier(fieldToMock: Field): Array<Annotation> = fieldToMock.declaredAnnotations
            .filterNot(::isLibAnnotation)
            .filter { annotation -> annotation.annotationClass.java.annotations.any { subannotation ->  subannotation.annotationClass.java == Qualifier::class.java }  }
            .toTypedArray()

    private fun isLibAnnotation(annotation: Annotation): Boolean = annotation.annotationClass.java == InjectMock::class.java || annotation.annotationClass.java == InjectSpy::class.java
}