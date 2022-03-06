package com.sw.spring.kotlin.sample.domain.user.service

import com.sw.spring.kotlin.sample.domain.user.domain.User
import com.sw.spring.kotlin.sample.domain.user.dto.SignUpRequest
import com.sw.spring.kotlin.sample.domain.user.exception.UserNotFoundException
import com.sw.spring.kotlin.sample.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService @Autowired constructor(val repository: UserRepository) {

    @Transactional
    fun doSignUp(request: SignUpRequest): User {
        return repository.save(request.toEntity())
    }

    fun findUser(id: UUID): User {
        return repository.findByIdOrNull(id)
            ?: throw UserNotFoundException("Not Found User. id=$id")
    }

    fun findUserByName(name: String): List<User> {
        return repository.findByName(name)
    }
}