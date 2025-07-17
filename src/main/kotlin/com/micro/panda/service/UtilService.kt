package com.micro.panda.service

import com.micro.panda.service.utils.PasswordGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UtilService(

    @Value("\${server.pg.pattern}")
    val pattern: String,

    val passwordGenerator: PasswordGenerator
) {

    fun generatePassword(): String {
        return passwordGenerator.generatePassword(pattern)
    }
}