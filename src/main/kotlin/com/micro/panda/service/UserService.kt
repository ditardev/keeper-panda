package com.micro.panda.service

import com.micro.panda.model.entity.UserEntity
import com.micro.panda.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findOrCreate(userUUID: String?): UserEntity {
        return userRepository.findUserEntityByUuid(UUID.fromString(userUUID))
            ?: return userRepository.save(UserEntity(null, UUID.fromString(userUUID)))
    }
}