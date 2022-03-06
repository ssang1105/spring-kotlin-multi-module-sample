package com.sw.spring.kotlin.sample.apiclient.dolphin.dto

import javax.validation.constraints.NotBlank

data class DolphinCheckTokenRequest(
    @NotBlank
    val token: String
)