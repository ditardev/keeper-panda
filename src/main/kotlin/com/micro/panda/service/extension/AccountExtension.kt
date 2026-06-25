package com.micro.panda.service.extension

import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.MailEntity
import com.micro.panda.model.entity.TypeEntity
import com.micro.panda.model.entity.UserEntity

fun AccountEntity.toDto(): AccountDto = AccountDto(
    id = this.id,
    name = this.name,
    account = this.account,
    password = this.password,
    link = this.link,
    description = this.description,
    updated = this.updated,
    email = this.emailEntity?.email,
    type = this.typeEntity?.type,
)

fun AccountDto.toEntity(
    userEntity: UserEntity,
    emailEntity: MailEntity?,
    typeEntity: TypeEntity?
): AccountEntity = AccountEntity(
    id = this.id,
    name = this.name,
    account = this.account,
    password = this.password,
    link = this.link,
    description = this.description,
    updated = this.updated,
    userEntity = userEntity,
    emailEntity = emailEntity,
    typeEntity = typeEntity,
)

fun Iterable<AccountEntity>.toDtoList(): List<AccountDto> {
    return this.map { it.toDto() }
}

fun Iterable<AccountDto>.toEntityList(
    userEntity: UserEntity,
    resolveMail: (String?) -> MailEntity?,
    resolveType: (String?) -> TypeEntity?,
): List<AccountEntity> {
    return this.map { dto ->
        dto.toEntity(
            userEntity = userEntity,
            emailEntity = resolveMail(dto.email),
            typeEntity = resolveType(dto.type),
        )
    }
}