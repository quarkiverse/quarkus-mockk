package io.quarkiverse.test.junit.mockk.internal

import io.mockk.spyk
import io.quarkiverse.test.junit.mockk.InjectSpy
import io.quarkus.arc.ClientProxy
import io.quarkus.test.junit.callback.QuarkusTestAfterConstructCallback
import java.lang.reflect.Field

class CreateMockkSpiesCallback: QuarkusTestAfterConstructCallback {
    override fun afterConstruct(testInstance: Any?) {
        testInstance?.let{ instance ->
            var current = instance::class.java
            while(current.superclass != null) {
                for (field in current.declaredFields) {
                    if (field.isAnnotationPresent(InjectSpy::class.java)) {
                        val beanInstance = ReflectionUtils.getBeanInstance(testInstance, field, InjectSpy::class.java)
                        val spy = createSpyAndSetTestField(testInstance, field, beanInstance)
                        MocksTracker.track(testInstance, spy, beanInstance)
                    }
                }
                current = current.superclass
            }
        }
    }

    private fun createSpyAndSetTestField(testInstance: Any, field: Field, beanInstance: Any): Any {
        val spy = spyk(ClientProxy.unwrap(beanInstance))
        if (field.trySetAccessible()) {
            field.set(testInstance, spy)
        }
        return spy
    }
}