package com.micro.panda.service.impl

import com.micro.panda.model.entity.MailEntity
import com.micro.panda.repository.MailRepository
import com.micro.panda.service.MailService
import org.springframework.stereotype.Service

@Service
class MailServiceImpl(
    private val mailRepository: MailRepository
) : MailService {
    override fun findOrCreate(userEmail: String?): MailEntity {
        return userEmail?.let { mailRepository.findByEmail(it) }
            ?: return mailRepository.save(MailEntity(null, userEmail));
    }
}