package com.sw.spring.kotlin.sample.domain.user.service

import com.sw.spring.kotlin.sample.apiclient.dolphin.DolphinClient
import com.sw.spring.kotlin.sample.apiclient.dolphin.dto.DolphinAuthorizeResponse
import org.jeasy.random.EasyRandom
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono

@ActiveProfiles("test")
@SpringBootTest
internal class AuthServiceTest @Autowired constructor(val sut: AuthService) {

    @MockBean
    lateinit var dolphinClient: DolphinClient
    lateinit var random: EasyRandom;

    @BeforeEach
    fun init() {
        random = EasyRandom()
    }

    @Test
    fun login() {
        // given
        val accessToken = random.nextObject(String::class.java)
        val mockedResponse = Mono.just(
            DolphinAuthorizeResponse(
                accessToken = accessToken,
                tokenType = "A",
                refreshToken = "B",
                scope = "C",
                expiresIn = 1
            )
        )
        whenever(dolphinClient.authorize(any(), any())).thenReturn(mockedResponse)

        // when
        val result = sut.login("TEST-ID", "TEST-PASSWORD");

        // then
        Assertions.assertNotNull(result)
        Assertions.assertEquals(result, accessToken)
    }
}