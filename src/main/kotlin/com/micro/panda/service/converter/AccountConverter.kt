package com.micro.panda.service.converter

import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.UserEntity
import com.micro.panda.service.MailService
import com.micro.panda.service.TypeService
import com.micro.panda.service.UserService
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class AccountConverter(
    private val mailService: MailService,
    private val typeService: TypeService,
) {

    fun convertToEntities(userEntity: UserEntity, dtos: List<AccountDto>): List<AccountEntity> {
        return dtos.stream()
            .map { it -> convertToEntity(userEntity, it) }
            .collect(Collectors.toList())
    }

    fun convertToEntity(userEntity: UserEntity, dto: AccountDto): AccountEntity {
        return AccountEntity(
            id = dto.id,
            name = dto.name,
            account = dto.account,
            password = dto.password,
            link = dto.link,
            description = dto.description,
            updated = dto.updated,
            userEntity = userEntity,
            emailEntity = mailService.findOrCreate(dto.email),
            typeEntity = typeService.findOrCreate(dto.type),
        )
    }

    fun convertToDtos(entityList: List<AccountEntity>): List<AccountDto> {
        return entityList.stream()
            .map { it -> convertToDto(it) }
            .collect(Collectors.toList())
    }

    fun convertToDto(entity: AccountEntity): AccountDto {
        return AccountDto(
            id = entity.id,
            name = entity.name,
            account = entity.account,
            password = entity.password,
            link = entity.link,
            description = entity.description,
            updated = entity.updated,
            email = entity.emailEntity?.email,
            type = entity.typeEntity?.type,
        )
    }
}