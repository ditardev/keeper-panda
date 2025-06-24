package com.micro.panda.repository

import com.micro.panda.model.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long> {
    fun findAllByUserEntityId(userEntityId: Long): List<AccountEntity>

}