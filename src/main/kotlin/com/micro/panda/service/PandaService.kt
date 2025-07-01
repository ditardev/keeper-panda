package com.micro.panda.service

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.panda.appconfig.exceptions.ResourceAlreadyExistException
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
        if(accountRepository.existsByUserEntityAndName(userEntity, accountDto.name!!)){
            throw ResourceAlreadyExistException("Account with name ${accountDto.name} already exists")
        }
        val accountEntity = accountRepository.save(converter.convertToEntity(userEntity, accountDto))
        return converter.convertToDto(accountEntity)
    }

    fun update(userUUID: String, accountDto: AccountDto): AccountDto {
        val userEntity = userService.findOrCreate(userUUID)
        if(!accountRepository.notExistById(accountDto.id!!)){
            throw ResourceNotFoundException("Account with name ${accountDto.name} not found")
        }
        val accountEntity = accountRepository.save(converter.convertToEntity(userEntity, accountDto))
        return converter.convertToDto(accountEntity)
    }

    fun delete(userUUID: String, idList: List<Long>): Int {
        val userEntity = userService.findOrCreate(userUUID)
        val existedIdList: List<Long> = emptyList()
        idList.forEach { id ->
            if(accountRepository.notExistsByUserEntityAndId(userEntity, id)){
                throw ResourceNotFoundException("Account with id $id not found")
            }
            existedIdList.plus(id)
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