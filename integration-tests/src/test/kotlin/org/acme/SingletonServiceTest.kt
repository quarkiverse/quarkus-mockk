package org.acme

import io.mockk.every
import io.mockk.verify
import io.quarkiverse.test.junit.mockk.InjectMock

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
class MockSingletonBeanTest {

    @InjectMock(convertScopes = true)
    lateinit var bean: SingletonService

    @Test
    fun `Should be able to use a mocked DataService`() {
        every { bean.greet() } returns "mocked"
        Assertions.assertThat(bean.greet()).isEqualTo("mocked")
        verify { bean.greet() }
    }
}