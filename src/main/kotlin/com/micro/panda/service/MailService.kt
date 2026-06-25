package com.micro.panda.service

import com.micro.panda.model.entity.MailEntity
import com.micro.panda.repository.MailRepository
import org.springframework.stereotype.Service

interface MailService {
    fun findOrCreate(userEmail: String?): MailEntity
}