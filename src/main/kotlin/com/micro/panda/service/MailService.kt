package com.micro.panda.service

import com.micro.panda.model.entity.MailEntity
import com.micro.panda.repository.MailRepository
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailRepository: MailRepository
) {
    fun findOrCreate(userEmail: String?): MailEntity {
        return userEmail?.let { mailRepository.findByEmail(it) }
            ?: return mailRepository.save(MailEntity(null, userEmail));
    }
}