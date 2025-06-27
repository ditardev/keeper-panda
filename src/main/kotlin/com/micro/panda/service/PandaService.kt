package com.micro.panda.service

import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.dto.UploadDto
import com.micro.panda.repository.AccountRepository
import com.micro.panda.service.converter.AccountConverter
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class PandaService(
    private val accountRepository: AccountRepository,
    private val converter: AccountConverter,
    private val userService: UserService,
    private val accountConverter: AccountConverter,
) {

    fun selectAll(userUUID: String?): List<AccountDto>? {
        val userEntity = userService.findOrCreate(userUUID)
        val accounts = accountRepository.findAllByUserEntity(userEntity)
        return accounts?.let { converter.convertToDtos(it) }
    }

//    fun selectByPage(userUUID: String, pageNumber: Int, pageSize: Int): List<AccountDto>? {
//        val userEntity = userService.findOrCreate(userUUID)
//        val accounts = userEntity.id?.let { accountRepository.findAllByUserEntityId(it) }
//        return accounts?.let { accountConverter.convertToDtos(it) }
//    }

    fun create(userUUID: String, accountDto: AccountDto): AccountDto {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntity = accountRepository.save(converter.convertToEntity(userEntity, accountDto))
        return converter.convertToDto(accountEntity)
    }

    fun update(userUUID: String, accountDto: AccountDto): AccountDto {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntity = accountRepository.save(converter.convertToEntity(userEntity, accountDto))
        return converter.convertToDto(accountEntity)
    }

    fun delete(userUUID: String, accountDto: AccountDto) {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntity = accountRepository.findById(accountDto.id!!)
        accountRepository.delete(accountEntity.get())
    }

    fun deleteList(userUUID: String, accountsDto: ArrayList<AccountDto>) {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntities = accountConverter.convertToEntities(userEntity, accountsDto)
        accountRepository.deleteAllByUserEntity(userEntity)
    }

    fun upload(userUUID: String, uploadDto: UploadDto): UploadDto {
        val userEntity = userService.findOrCreate(userUUID)
        val dtoEntities = converter.convertToEntities(userEntity, uploadDto.inputList)
        val accountsEntities = accountRepository.findAllByUserEntity(userEntity)

        val resultEntitiesList = (accountsEntities + dtoEntities).distinctBy { Pair(it.name, it.password) }

        val savedEntities = accountRepository.saveAll(resultEntitiesList)

        return UploadDto(
            count = abs(accountsEntities.size - resultEntitiesList.size),
            inputList = accountConverter.convertToDtos(savedEntities)
        )
    }

    fun replace(userUUID: String, uploadDto: UploadDto): UploadDto {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntities = accountConverter.convertToEntities(userEntity, uploadDto.inputList)

        accountRepository.deleteAllByUserEntity(userEntity)

        val savedEntities = accountRepository.saveAll(accountEntities)
        val savedDtoList = accountConverter.convertToDtos(savedEntities)

        return UploadDto(
            count = savedDtoList.size,
            inputList = savedDtoList
        )
    }

    fun download(userUUID: String): List<AccountDto> {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntities = accountRepository.findAllByUserEntity(userEntity)
        return accountConverter.convertToDtos(accountEntities)
    }

}