package com.micro.panda.service

import com.micro.panda.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
) {

}