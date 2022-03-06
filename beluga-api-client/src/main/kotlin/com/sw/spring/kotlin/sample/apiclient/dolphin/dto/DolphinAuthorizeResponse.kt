package com.sw.spring.kotlin.sample.apiclient.dolphin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DolphinAuthorizeResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
    val scope: String
)
