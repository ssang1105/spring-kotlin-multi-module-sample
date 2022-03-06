package com.sw.spring.kotlin.sample.apiclient.dolphin

import com.sw.spring.kotlin.sample.apiclient.dolphin.dto.DolphinAuthorizeResponse
import com.sw.spring.kotlin.sample.apiclient.dolphin.dto.DolphinErrorResponse
import com.sw.spring.kotlin.sample.apiclient.dolphin.exceptions.DolphinAuthorizeFailureException
import com.sw.spring.kotlin.sample.apiclient.dolphin.exceptions.DolphinClientException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


@Component
class DolphinClient constructor(@Qualifier("dolphinWebClient") val webclient: WebClient) {

    private val logger = KotlinLogging.logger {}

    companion object {
        const val DOLPHIN_GRAN_TYPE = "password"
    }

    fun authorize(id: String, password: String): Mono<DolphinAuthorizeResponse> {
        val params = LinkedMultiValueMap<String, String>()
        params["grant_type"] = DOLPHIN_GRAN_TYPE
        params["username"] = id
        params["password"] = password

        return webclient.post()
            .uri("/oauth/token")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromFormData(params))
            .retrieve()
            .onStatus(HttpStatus::isError, this::handleErrorResponse)
            .bodyToMono(DolphinAuthorizeResponse::class.java)
            .doOnNext { response -> logger.info { response } }

    }

    private fun handleErrorResponse(response: ClientResponse): Mono<Throwable> {
        return response.bodyToMono(DolphinErrorResponse::class.java)
            .map { errorResponse ->
                {
                    if (response.statusCode() == HttpStatus.UNAUTHORIZED) {
                        throw DolphinAuthorizeFailureException(errorResponse.errorDescription)
                    }
                    throw DolphinClientException(errorResponse)
                }
            }
            .flatMap { exception -> Mono.error(exception) }
    }
}


