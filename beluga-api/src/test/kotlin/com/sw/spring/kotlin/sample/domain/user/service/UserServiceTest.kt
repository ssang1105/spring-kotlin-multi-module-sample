package com.sw.spring.kotlin.sample.domain.user.service

import com.sw.spring.kotlin.sample.domain.user.dto.SignUpRequest
import com.sw.spring.kotlin.sample.domain.user.exception.UserNotFoundException
import com.sw.spring.kotlin.sample.domain.user.model.UserStatus
import org.jeasy.random.EasyRandom
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.util.*

@ActiveProfiles("test")
@SpringBootTest
internal class UserServiceTest @Autowired constructor(val sut: UserService) {

    lateinit var random: EasyRandom;

    @BeforeEach
    fun init() {
        random = EasyRandom()
    }

    @Test
    fun doSignUp() {
        // given
        val request = random.nextObject(SignUpRequest("test").javaClass)

        // when
        val result = sut.doSignUp(request)

        // then
        assertNotNull(result)
        assertEquals(result.id.toString().length, UUID.randomUUID().toString().length)
        assertEquals(result.name, request.name)
        assertEquals(result.status, UserStatus.ACTIVE)
        assertTrue(result.createdAt.isBefore(LocalDateTime.now()))
        assertTrue(result.modifiedAt.isBefore(LocalDateTime.now()))
    }

    @Test
    fun findUser() {
        // given
        val signUpResult = sut.doSignUp(random.nextObject(SignUpRequest("test").javaClass));

        // when
        val result = sut.findUser(signUpResult.id!!)

        // then
        assertNotNull(result)
        assertEquals(result.id.toString().length, UUID.randomUUID().toString().length)
        assertEquals(result.name, signUpResult.name)
        assertEquals(result.status, signUpResult.status)
        assertEquals(result.createdAt, signUpResult.createdAt)
        assertEquals(result.modifiedAt, signUpResult.modifiedAt)
    }

    @Test
    fun findUser_notfound() {
        // given & when & then
        Assertions.assertThrows(UserNotFoundException::class.java) {
            sut.findUser(UUID.randomUUID())
        }
    }

    @Test
    fun findUserByName() {
        // given
        val signUpResult = sut.doSignUp(random.nextObject(SignUpRequest("test").javaClass));

        // when
        val result = sut.findUserByName(signUpResult.name).first { it.id!! == signUpResult.id }

        // then
        assertNotNull(result)
        assertEquals(result.id.toString().length, UUID.randomUUID().toString().length)
        assertEquals(result.name, signUpResult.name)
        assertEquals(result.status, signUpResult.status)
        assertEquals(result.createdAt, signUpResult.createdAt)
        assertEquals(result.modifiedAt, signUpResult.modifiedAt)
    }
}