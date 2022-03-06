package com.sw.spring.kotlin.sample.domain.user.dto

import com.sw.spring.kotlin.sample.domain.user.domain.User

data class SignUpRequest(val name: String) {
    fun toEntity(): User {
        return User(id = null, name = name);
    }
}
