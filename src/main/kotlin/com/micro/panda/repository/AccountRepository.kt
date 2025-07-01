package com.micro.panda.repository

import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long> {

    fun findAllByUserEntity(userEntity: UserEntity): List<AccountEntity>

    fun deleteByUserEntityAndId(userEntity: UserEntity, id: Long)
    fun deleteAllByUserEntity(userEntity: UserEntity)

    fun existsByUserEntityAndName(userEntity: UserEntity, name: String): Boolean
    fun notExistsByUserEntityAndId(userEntity: UserEntity, id: Long): Boolean
    fun notExistById(id: Long): Boolean

}