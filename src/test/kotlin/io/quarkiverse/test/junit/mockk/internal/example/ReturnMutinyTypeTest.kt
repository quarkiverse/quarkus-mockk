package io.quarkiverse.test.junit.mockk.internal.example

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.mutiny.Uni
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.enterprise.context.ApplicationScoped

@QuarkusTest
class ReturnMutinyTypeTest {

    @InjectMock lateinit var service: Service

    @Test
    fun `should mock mutiny type`() {
        every { service.greet() } returns Uni.createFrom().item("mock")

        val resp = service.greet().await().indefinitely()
        assertThat(resp).isEqualTo("mock")
    }


    @ApplicationScoped
    open class Service() {
        fun greet(): Uni<String> = Uni.createFrom().item("test")
    }

}