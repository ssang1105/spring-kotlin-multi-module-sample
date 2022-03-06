package com.sw.spring.kotlin.sample.apiclient.dolphin.exceptions

import com.sw.spring.kotlin.sample.apiclient.dolphin.dto.DolphinErrorResponse

class DolphinClientException : RuntimeException {

    private val response: DolphinErrorResponse;

    constructor(message: String, e: RuntimeException, response: DolphinErrorResponse) : super(message, e) {
        this.response = response
    }

    constructor(response: DolphinErrorResponse) : super() {
        this.response = response
    }

}