package com.micro.panda.repository

import com.micro.panda.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Int> {

    fun findUserEntityByUuid(uuid: UUID): UserEntity?
}