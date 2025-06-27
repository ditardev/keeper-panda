package com.micro.panda.repository

import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long> {
    fun findAllByUserEntity(userEntity: UserEntity): List<AccountEntity>
    fun deleteByUserEntity(userEntity: UserEntity)
    fun deleteAllByUserEntity(userEntity: UserEntity)

}