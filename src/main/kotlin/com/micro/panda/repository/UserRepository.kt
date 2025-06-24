package com.micro.panda.repository

import com.micro.panda.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Int> {

    fun findByUsername(username: String): UserEntity?
}