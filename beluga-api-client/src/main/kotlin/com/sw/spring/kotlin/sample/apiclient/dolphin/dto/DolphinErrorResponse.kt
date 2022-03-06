package com.sw.spring.kotlin.sample.apiclient.dolphin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DolphinErrorResponse(
    val error: String,
    @JsonProperty("error_description")
    val errorDescription: String
)