package com.micro.panda.service

import com.micro.panda.model.entity.TypeEntity

interface TypeService {
    fun findOrCreate(type: String?): TypeEntity
}