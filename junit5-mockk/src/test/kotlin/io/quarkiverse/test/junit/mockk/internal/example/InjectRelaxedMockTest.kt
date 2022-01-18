package io.quarkiverse.test.junit.mockk.internal.example

import io.mockk.verify
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@QuarkusTest
class InjectRelaxedMockTest {

    @Inject
    lateinit var firstService: FirstService

    @InjectMock(relaxed = true)
    lateinit var secondService: SecondService


    @Test
    fun `should run unit function`() {
        firstService.run()

        verify { secondService.run() }
    }

    @ApplicationScoped
    open class FirstService(val secondService: SecondService) {
        fun run(): Unit = secondService.run()
    }

    @ApplicationScoped
    open class SecondService {
        fun run(): Unit = println("run ran")
    }

}