package com.sw.spring.kotlin.sample.apiclient.kis

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.reactive.function.client.WebClient

class KisClient constructor(@Qualifier("kisWebClient") val webclient: WebClient) {

}