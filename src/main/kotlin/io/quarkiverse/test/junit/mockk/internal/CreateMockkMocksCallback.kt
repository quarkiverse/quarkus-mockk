package io.quarkiverse.test.junit.mockk.internal

import io.mockk.mockkClass
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.arc.ClientProxy

import io.quarkus.test.junit.callback.QuarkusTestAfterConstructCallback
import java.lang.reflect.Field
import kotlin.jvm.internal.Reflection

class CreateMockkMocksCallback: QuarkusTestAfterConstructCallback {
    override fun afterConstruct(testInstance: Any?): Unit {
        testInstance?.let{ instance ->
            var current = instance::class.java
            while(current.superclass != null) {
                for (field in current.declaredFields) {
                    if (field.isAnnotationPresent(InjectMock::class.java)) {
                        val annotation = field.getAnnotation(InjectMock::class.java)
                        val beanInstance = ReflectionUtils.getBeanInstance(testInstance, field, InjectMock::class.java)
                        val mock = createAndSetMock(testInstance, field, beanInstance, annotation)
                        MocksTracker.track(testInstance, mock, beanInstance)
                    }
                }
                current = current.superclass
            }
        }
    }

    private fun createAndSetMock(testInstance: Any, field: Field, beanInstance: Any, annotation: InjectMock): Any {
        var beanClass = beanInstance::class
        if (ClientProxy::class.java.isAssignableFrom(beanClass.java)) {
            if (beanClass.java.superclass != Object::class.java) {
                beanClass = Reflection.createKotlinClass(beanClass.java.superclass)
            }
        }
        val mock = mockkClass(beanClass, relaxed = annotation.relaxed, relaxUnitFun = annotation.relaxUnitFun)
        if (field.trySetAccessible()) {
            field.set(testInstance, mock)
        }
        return mock
    }
}