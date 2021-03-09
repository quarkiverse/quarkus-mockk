package io.quarkiverse.test.junit.mockk.internal.example

import io.mockk.every
import io.mockk.verify
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@QuarkusTest
class InjectionMockTest {

    @Inject
    private lateinit var firstService: FirstService

    @InjectMock
    private lateinit var secondService: SecondService

    @Test
    fun `should respond test`() {
        every { secondService.greet() } returns "test"
        assertThat(firstService.greet()).isEqualTo("test")
    }

    @Test
    fun `should respond second`() {
        every { secondService.greet() } returns "second"
        assertThat(firstService.greet()).isEqualTo("second")
        verify { secondService.greet() }
    }


    @ApplicationScoped
    open class FirstService(val secondService: SecondService) {
        fun greet(): String = secondService.greet()
    }

    @ApplicationScoped
    open class SecondService {
        fun greet(): String = "Second service"
    }

}