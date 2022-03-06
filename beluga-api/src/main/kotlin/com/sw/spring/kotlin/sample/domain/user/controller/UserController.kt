package com.sw.spring.kotlin.sample.domain.user.controller

import com.sw.spring.kotlin.sample.domain.user.dto.SignUpRequest
import com.sw.spring.kotlin.sample.domain.user.dto.UserResponse
import com.sw.spring.kotlin.sample.domain.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(val service: UserService) {

    @PostMapping
    fun create(@RequestBody request: SignUpRequest): ResponseEntity<UserResponse> {
        val user = service.doSignUp(request);
        return ResponseEntity.ok(UserResponse(user.id, user.name));
    }

    @GetMapping("/{id}")
    fun fetch(@PathVariable id: UUID): ResponseEntity<UserResponse> {
        val user = service.findUser(id)
        return ResponseEntity.ok(UserResponse(user.id, user.name));
    }
}