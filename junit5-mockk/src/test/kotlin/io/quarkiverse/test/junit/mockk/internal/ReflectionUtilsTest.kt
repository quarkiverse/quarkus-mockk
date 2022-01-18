package io.quarkiverse.test.junit.mockk.internal

import io.quarkiverse.test.junit.mockk.InjectMock
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.junit.jupiter.api.Test
import java.lang.reflect.Field

internal class ReflectionUtilsTest {

    @Test
    fun `should find qualifier annotation`() {
        val c = TestClassWithBeans::class.java
        val restClientField: Field = c.declaredFields.first { it.isAnnotationPresent(RestClient::class.java) }

        val qualifiers = ReflectionUtils.getQualifier(restClientField)

        assertThat(qualifiers).hasSize(1)
        assertThat(qualifiers[0].annotationClass.java).isEqualTo(RestClient::class.java)
    }


    class TestClassWithBeans {

        @InjectMock
        @RestClient
        lateinit var rest: String
    }
}