package com.sw.spring.kotlin.sample.domain.user.service

import com.sw.spring.kotlin.sample.apiclient.dolphin.DolphinClient
import org.springframework.stereotype.Service

@Service
class AuthService(val dolphinClient: DolphinClient) {

    fun login(id: String, password: String): String? {
        return dolphinClient.authorize(id, password)
            .map { response -> response.accessToken }
            .block()
    }
}