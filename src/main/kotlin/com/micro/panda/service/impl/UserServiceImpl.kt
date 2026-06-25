package com.micro.panda.service.impl

import com.micro.panda.model.entity.UserEntity
import com.micro.panda.repository.UserRepository
import com.micro.panda.service.UserService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
): UserService {
    override fun findOrCreate(userUUID: String?): UserEntity {
        return userRepository.findUserEntityByUuid(UUID.fromString(userUUID))
            ?: return userRepository.save(UserEntity(null, UUID.fromString(userUUID)))
    }
}