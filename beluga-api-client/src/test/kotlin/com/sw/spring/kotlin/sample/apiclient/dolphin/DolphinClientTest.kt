package com.sw.spring.kotlin.sample.apiclient.dolphin

import com.fasterxml.jackson.databind.ObjectMapper
import com.sw.spring.kotlin.sample.apiclient.dolphin.dto.DolphinAuthorizeResponse
import com.sw.spring.kotlin.sample.apiclient.dolphin.exceptions.DolphinAuthorizeFailureException
import com.sw.spring.kotlin.sample.apiclient.dolphin.exceptions.DolphinClientException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import java.lang.String


internal class DolphinClientTest {

    lateinit var objectMapper: ObjectMapper
    lateinit var mockWebServer: MockWebServer
    lateinit var sut: DolphinClient

    @BeforeEach
    fun setUp() {
        objectMapper = ObjectMapper()
        mockWebServer = MockWebServer()
        mockWebServer.start();

        val baseUrl = String.format("http://localhost:%s", mockWebServer.port)
        sut = DolphinClient(WebClient.builder()
            .baseUrl(baseUrl)
            .build())
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun authorize_success() {
        // given
        val sampleResponse = this.javaClass.getResource("/response/dolphin_authorize_success_response.json")!!.readText()
        mockWebServer.enqueue(
            MockResponse()
                .setBody(sampleResponse)
                .setResponseCode(HttpStatus.OK.value())
                .addHeader("Content-Type", "application/json")
        )

        // when
        val result: DolphinAuthorizeResponse? = sut.authorize("test-id", "test-password").block()

        // then
        Assertions.assertEquals(result!!.accessToken, "TEST-ACCESS-TOKEN")
        Assertions.assertEquals(result.tokenType, "TEST-TOKE-TYPE")
        Assertions.assertEquals(result.refreshToken, "TEST-REFRESH-TOKEN")
        Assertions.assertEquals(result.expiresIn, 3600)
        Assertions.assertEquals(result.scope, "TEST")
    }

    @Test
    fun authorize_unauthorized() {
        // given
        val sampleResponse = this.javaClass.getResource("/response/dolphin_authorize_failure_response.json")!!.readText()
        mockWebServer.enqueue(
            MockResponse()
                .setBody(sampleResponse)
                .setResponseCode(HttpStatus.UNAUTHORIZED.value())
                .addHeader("Content-Type", "application/json")
        )

        // when & then
        Assertions.assertThrows(DolphinAuthorizeFailureException::class.java) {
            sut.authorize("test-id", "test-password").block()
        }
    }

    @Test
    fun authorize_unexpected() {
        // given
        val sampleResponse = this.javaClass.getResource("/response/dolphin_authorize_failure_response.json")!!.readText()
        mockWebServer.enqueue(
            MockResponse()
                .setBody(sampleResponse)
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .addHeader("Content-Type", "application/json")
        )

        // when & then
        Assertions.assertThrows(DolphinClientException::class.java) {
            sut.authorize("test-id", "test-password").block()
        }
    }
}


