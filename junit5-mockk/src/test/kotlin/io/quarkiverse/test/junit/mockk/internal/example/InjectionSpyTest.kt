package io.quarkiverse.test.junit.mockk.internal.example

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectSpy
import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@QuarkusTest
class InjectionSpyTest {

    @Inject
    lateinit var firstService: FirstService

    @InjectSpy
    lateinit var secondService: SecondService

    @Test
    fun `should return normal response`() {
        assertThat(firstService.greet()).isEqualTo("Second service")
    }

    @Test
    fun `should respond second`() {
        every { secondService.greet() } returns "second"
        assertThat(firstService.greet()).isEqualTo("second")
    }


    @ApplicationScoped
    open class FirstService(val secondService: SecondService) {
        fun greet(): String= secondService.greet()
    }

    @ApplicationScoped
    open class SecondService {
        fun greet(): String = "Second service"
    }

}