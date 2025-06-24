package com.micro.panda.service

import com.micro.panda.model.entity.TypeEntity
import com.micro.panda.repository.TypeRepository
import org.springframework.stereotype.Service

@Service
class TypeService(
    private val typeRepository: TypeRepository,
) {
    fun findOrCreate(type: String?): TypeEntity {
        return type?.let { typeRepository.findByType(it) }
            ?: return typeRepository.save(TypeEntity(null, type))
    }
}