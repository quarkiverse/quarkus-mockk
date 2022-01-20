package org.acme

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.`is`

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@QuarkusTest
class NestedTest {

    @InjectMock
    lateinit var messageService: MessageService

    @Nested
    inner class ActualTest {

        @InjectMock
        lateinit var suffixService: SuffixService

        @Test
        fun greet() {
            every { suffixService.getSuffix() } returns "!!"
            every { messageService.getMessage() } returns "mocked"

            given()
                .get("/message")
                .then()
                .statusCode(200)
                .body(`is`("mocked !!"))
        }

        @Test
        fun greetAgain() {
            every { suffixService.getSuffix() } returns "!!"
            every { messageService.getMessage() } returns "new mocked"

            given()
                .get("/message")
                .then()
                .statusCode(200)
                .body(`is`("new mocked !!"))
        }

    }
}