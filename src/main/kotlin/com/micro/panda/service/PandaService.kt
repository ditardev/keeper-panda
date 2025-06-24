package com.micro.panda.service

import com.micro.panda.model.dto.AccountDto
import com.micro.panda.repository.AccountRepository
import com.micro.panda.service.converter.AccountConverter
import org.springframework.stereotype.Service

@Service
class PandaService(
    private val accountRepository: AccountRepository,
    private val accountConverter: AccountConverter,
    private val userService: UserService,
) {
    //TODO select by pages
    fun selectAll(userUUID: String?): List<AccountDto>? {
        val userEntity = userService.findOrCreate(userUUID)
        val accounts = userEntity.id?.let { accountRepository.findAllByUserEntityId(it) }
        return accounts?.let { accountConverter.convertToDtos(it) }
    }

    fun create(dto: AccountDto): AccountDto {
        val accountEntity = accountRepository.save(accountConverter.convertToEntity(dto))
        return accountConverter.convertToDto(accountEntity)
    }

    fun update(dto: AccountDto): AccountDto {
        val accountEntity = accountRepository.save(accountConverter.convertToEntity(dto))
        return accountConverter.convertToDto(accountEntity)
    }

    fun delete(dto: AccountDto) {
        val accountEntity = accountRepository.findById(dto.id!!)
        accountRepository.delete(accountEntity.get())
    }
}