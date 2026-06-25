package com.micro.panda.service.impl

import com.micro.panda.service.UtilService
import com.micro.panda.service.utils.PasswordGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UtilServiceImpl(

    @Value("\${server.pg.pattern}")
    val pattern: String,

) : UtilService {

    override fun generatePassword(): String {
        return PasswordGenerator.generatePassword(pattern)
    }
}