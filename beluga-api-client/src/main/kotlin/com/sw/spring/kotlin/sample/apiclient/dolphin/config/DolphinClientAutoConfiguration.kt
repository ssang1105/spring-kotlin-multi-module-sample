package com.sw.spring.kotlin.sample.apiclient.dolphin.config

import com.sw.spring.kotlin.sample.apiclient.dolphin.config.DolphinClientAutoConfiguration.DolphinClientProperties
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.hibernate.validator.constraints.URL
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.validation.annotation.Validated
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive


@Configuration
@ConditionalOnProperty(prefix = "api.client.dolphin", name = ["enable"], havingValue = "true")
@EnableConfigurationProperties(DolphinClientProperties::class)
class DolphinClientAutoConfiguration {

    @Bean(name = ["dolphinWebClient"])
    fun dolphinWebClient(properties: DolphinClientProperties): WebClient {
        return WebClient.builder()
            .baseUrl(properties.baseUrl)
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.connectionTimeoutMillis)
                        .doOnConnected { connection ->
                            connection.addHandler(
                                ReadTimeoutHandler(
                                    properties.connectionReadTimeoutMillis, TimeUnit.MILLISECONDS
                                )
                            )
                        })
            )
            .defaultHeaders { header -> header.setBasicAuth(properties.clientId, properties.clientSecret) }
            .build()
    }

    @Validated
    @ConstructorBinding
    @ConfigurationProperties("api.client.dolphin")
    data class DolphinClientProperties(

        @URL
        @NotBlank
        val baseUrl: String,

        @NotBlank
        val clientId: String,

        @NotBlank
        val clientSecret: String,

        @Positive
        @NotNull
        val connectionTimeoutMillis: Int,

        @Positive
        @NotNull
        val connectionReadTimeoutMillis: Long,

        @NotNull
        val enable: Boolean

        )
}

