package com.micro.panda.service.impl

import com.micro.panda.model.entity.TypeEntity
import com.micro.panda.repository.TypeRepository
import com.micro.panda.service.TypeService
import org.springframework.stereotype.Service

@Service
class TypeServiceImpl(
    private val typeRepository: TypeRepository,
): TypeService {
    override fun findOrCreate(type: String?): TypeEntity {
        return type?.let { typeRepository.findByType(it) }
            ?: return typeRepository.save(TypeEntity(null, type))
    }
}