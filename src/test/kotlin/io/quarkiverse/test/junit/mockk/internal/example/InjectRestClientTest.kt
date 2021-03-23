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
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@QuarkusTest
class InjectRestClientTest {

    @Inject
    private lateinit var greeterService: GreeterService

    @InjectMock
    @RestClient
    private lateinit var serviceClient: ServiceClient

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