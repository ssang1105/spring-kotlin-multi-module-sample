package com.sw.spring.kotlin.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.sw.spring.kotlin.sample", "com.sw.spring.kotlin.sample.apiclient"])
class SpringKotlinSampleApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinSampleApplication>(*args)
}