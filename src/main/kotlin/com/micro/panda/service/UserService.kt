package com.micro.panda.service

import com.micro.panda.model.entity.UserEntity
import com.micro.panda.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

interface UserService {
    fun findOrCreate(userUUID: String?): UserEntity
}