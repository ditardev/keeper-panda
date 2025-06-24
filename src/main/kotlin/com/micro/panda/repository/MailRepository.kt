package com.micro.panda.repository

import com.micro.panda.model.entity.MailEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository : JpaRepository<MailEntity, Long> {

    fun findByEmail(email: String): MailEntity?
}