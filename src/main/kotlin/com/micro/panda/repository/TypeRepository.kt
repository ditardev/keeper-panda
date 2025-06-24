package com.micro.panda.repository

import com.micro.panda.model.entity.TypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TypeRepository : JpaRepository<TypeEntity, Long> {
    fun findByType(type: String): TypeEntity?
}