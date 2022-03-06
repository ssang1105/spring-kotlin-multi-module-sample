package com.sw.spring.kotlin.sample.domain.user.repository

import com.sw.spring.kotlin.sample.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByName(name: String): List<User>

}