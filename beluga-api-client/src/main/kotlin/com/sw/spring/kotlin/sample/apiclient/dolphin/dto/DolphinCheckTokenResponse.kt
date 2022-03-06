package com.sw.spring.kotlin.sample.apiclient.dolphin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DolphinCheckTokenResponse(
    val aud: List<String> = listOf(),
    val scope: List<String> = listOf(),
    @JsonProperty("user_name")
    val userName: String,
    val active: Boolean,
    val exp: Long,
    val clientId: String
)
