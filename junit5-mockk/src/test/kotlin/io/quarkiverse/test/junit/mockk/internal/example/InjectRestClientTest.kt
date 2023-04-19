package io.quarkiverse.test.junit.mockk.internal.example

import io.mockk.every
import io.mockk.verify
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.resteasy.annotations.jaxrs.PathParam
import org.junit.jupiter.api.Test
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@QuarkusTest
class InjectRestClientTest {

    @Inject
    lateinit var greeterService: GreeterService

    @InjectMock
    @RestClient
    lateinit var serviceClient: ServiceClient

    @Test
    fun `should use mocked serviceClient`() {
        every { serviceClient.getByName(any()) } returns "Quarkus"

        assertThat(greeterService.greet("quarkus")).isEqualTo("Hello Quarkus")

        verify { serviceClient.getByName("quarkus") }
    }


    @ApplicationScoped
    class GreeterService(@RestClient val serviceClient: ServiceClient) {
        fun greet(name: String) = "Hello ${serviceClient.getByName(name)}"
    }

    @ApplicationScoped
    @Path("/v2")
    @RegisterRestClient(configKey = "restclient.ServiceClient")
    interface ServiceClient {

        @GET
        @Path("/name/{name}")
        @Produces(MediaType.APPLICATION_JSON)
        fun getByName(@PathParam name: String): String
    }

}