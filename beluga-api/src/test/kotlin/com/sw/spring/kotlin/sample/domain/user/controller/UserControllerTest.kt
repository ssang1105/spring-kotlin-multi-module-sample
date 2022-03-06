package com.sw.spring.kotlin.sample.domain.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sw.spring.kotlin.sample.domain.user.domain.User
import com.sw.spring.kotlin.sample.domain.user.dto.SignUpRequest
import com.sw.spring.kotlin.sample.domain.user.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest
@AutoConfigureMockMvc
internal class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun create() {
        val uri = "/users"
        val userName = "TEST-NAME"
        val content: String = objectMapper.writeValueAsString(SignUpRequest(userName))
        val user = User(id = UUID.randomUUID(), name = userName)
        whenever(userService.doSignUp(any())).thenReturn(user)
        mockMvc.perform(
            MockMvcRequestBuilders.post(uri)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.id").value(user.id.toString()))
            .andExpect(jsonPath("\$.name").value(user.name))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun fetch() {
        val userId = UUID.randomUUID()
        val userName = "TEST-NAME"
        val uri = "/users/$userId"
        val user = User(id = userId, name = userName)
        whenever(userService.findUser(any())).thenReturn(user)

        mockMvc.perform(
            MockMvcRequestBuilders.get(uri)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.id").value(user.id.toString()))
            .andExpect(jsonPath("\$.name").value(user.name))
            .andDo(MockMvcResultHandlers.print())
    }

}