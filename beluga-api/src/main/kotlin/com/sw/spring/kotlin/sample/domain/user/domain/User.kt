package com.sw.spring.kotlin.sample.domain.user.domain

import com.sw.spring.kotlin.sample.global.domain.BaseEntity
import com.sw.spring.kotlin.sample.domain.user.model.UserStatus
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
class User(id: UUID?, val name: String) : BaseEntity(id) {
    var status: UserStatus = UserStatus.ACTIVE;
}