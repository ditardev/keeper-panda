package com.micro.panda.service

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.panda.appconfig.exceptions.ResourceAlreadyExistException
import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.ImportType
import com.micro.panda.model.dto.UploadDto
import com.micro.panda.model.UploadFileDto
import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.UserEntity
import com.micro.panda.repository.AccountRepository
import com.micro.panda.service.converter.AccountConverter
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class PandaService(
    private val accountRepository: AccountRepository,
    private val converter: AccountConverter,
    private val userService: UserService,
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
        if (accountRepository.existsByUserEntityAndName(userEntity, accountDto.name!!)) {
            throw ResourceAlreadyExistException("Account with name ${accountDto.name} already exists")
        }
        val accountEntity = accountRepository.save(converter.convertToEntity(userEntity, accountDto))
        return converter.convertToDto(accountEntity)
    }

    fun update(userUUID: String, accountDto: AccountDto): AccountDto {
        val userEntity = userService.findOrCreate(userUUID)
        if (!accountRepository.existsByUserEntityAndId(userEntity, accountDto.id!!)) {
            throw ResourceNotFoundException("Account with name ${accountDto.name} not found")
        }
        val accountEntity = accountRepository.save(converter.convertToEntity(userEntity, accountDto))
        return converter.convertToDto(accountEntity)
    }

    fun delete(userUUID: String, idList: List<Long>): Int {
        val userEntity = userService.findOrCreate(userUUID)
        val existedIdList = mutableListOf<Long>()
        for (id in idList) {
            if (!accountRepository.existsByUserEntityAndId(userEntity, id)) {
                throw ResourceNotFoundException("Account with id $id not found")
            }
            existedIdList.add(id)
        }
        existedIdList.forEach { id -> accountRepository.deleteById(id) }
        return existedIdList.size
    }

    fun upload(userUUID: String, uploadDto: UploadDto): UploadDto {
        val userEntity = userService.findOrCreate(userUUID)

        val dtoEntities = converter.convertToEntities(userEntity, uploadDto.inputList)
        val accountsEntities = accountRepository.findAllByUserEntity(userEntity)

        val resultEntitiesList = (accountsEntities + dtoEntities).distinctBy { Pair(it.name, it.password) }

        val savedEntities = accountRepository.saveAll(resultEntitiesList)

        return UploadDto(
            count = abs(accountsEntities.size - resultEntitiesList.size),
            inputList = converter.convertToDtos(savedEntities)
        )
    }

    fun replace(userUUID: String, uploadDto: UploadDto): UploadDto {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntities = converter.convertToEntities(userEntity, uploadDto.inputList)

        accountRepository.deleteAllByUserEntity(userEntity)

        val savedEntities = accountRepository.saveAll(accountEntities)
        val savedDtoList = converter.convertToDtos(savedEntities)

        return UploadDto(
            count = savedDtoList.size,
            inputList = savedDtoList
        )
    }

    fun upload(userUUID: String, uploadFileDto: UploadFileDto): Int {
        val userEntity = userService.findOrCreate(userUUID)
        val inputEntities = converter.convertToEntities(userEntity, uploadFileDto.json)
        when (uploadFileDto.type) {
            ImportType.IMPORT -> import(inputEntities)
            ImportType.REPLACE -> replace(userEntity, inputEntities)
        }
        return inputEntities.size
    }

    fun import(inputList: List<AccountEntity>) {
        accountRepository.saveAll(inputList)
    }

    fun replace(userEntity: UserEntity, inputList: List<AccountEntity>) {
        accountRepository.deleteAllByUserEntity(userEntity)
        accountRepository.saveAll(inputList)
    }


}