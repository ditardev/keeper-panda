package com.micro.panda.service.impl

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.panda.appconfig.exceptions.ResourceAlreadyExistException
import com.micro.panda.appconfig.utility.Messages
import com.micro.panda.model.ImportType
import com.micro.panda.model.UploadFileDto
import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.dto.UploadDto
import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.UserEntity
import com.micro.panda.repository.AccountRepository
import com.micro.panda.service.PandaService
import com.micro.panda.service.UserService
import com.micro.panda.service.converter.AccountConverter
import com.micro.panda.service.extension.toDto
import com.micro.panda.service.extension.toDtoList
import com.micro.panda.service.extension.toEntity
import com.micro.panda.service.extension.toEntityList
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class PandaServiceImpl(
    private val accountRepository: AccountRepository,
    private val converter: AccountConverter,
    private val userService: UserService,
    private val mailService: MailServiceImpl,
    private val typeService: TypeServiceImpl
) : PandaService {

    override fun selectAll(userUUID: String?): List<AccountDto>? {
        val userEntity = userService.findOrCreate(userUUID)
        val accounts = accountRepository.findAllByUserEntity(userEntity)
        return accounts.toDtoList()
    }

//    fun selectByPage(userUUID: String, pageNumber: Int, pageSize: Int): List<AccountDto>? {
//        val userEntity = userService.findOrCreate(userUUID)
//        val accounts = userEntity.id?.let { accountRepository.findAllByUserEntityId(it) }
//        return accounts?.let { accountConverter.convertToDtos(it) }
//    }

    override fun create(userUUID: String, accountDto: AccountDto): AccountDto {
        val userEntity = userService.findOrCreate(userUUID)
        if (accountRepository.existsByUserEntityAndName(userEntity, accountDto.name!!)) {
            throw ResourceAlreadyExistException(Messages.ALREADY_EXIST + accountDto.name)
        }
        val accountEntity = accountRepository.save(
            accountDto.toEntity(
                userEntity,
                mailService.findOrCreate(accountDto.email),
                typeService.findOrCreate(accountDto.type)
            ))
        return accountEntity.toDto()
    }

    override fun update(userUUID: String, accountDto: AccountDto): AccountDto {
        val userEntity = userService.findOrCreate(userUUID)
        if (!accountRepository.existsByUserEntityAndId(userEntity, accountDto.id!!)) {
            throw ResourceNotFoundException(Messages.NOT_FOUND + accountDto.name)
        }
        val accountEntity = accountRepository.save(
            accountDto.toEntity(
                userEntity,
                mailService.findOrCreate(accountDto.email),
                typeService.findOrCreate(accountDto.type)
            ))
        return accountEntity.toDto()
    }

    override fun delete(userUUID: String, idList: List<Long>): Int {
        val userEntity = userService.findOrCreate(userUUID)
        val existedIdList = mutableListOf<Long>()
        for (id in idList) {
            if (!accountRepository.existsByUserEntityAndId(userEntity, id)) {
                throw ResourceNotFoundException("${Messages.NOT_FOUND} $id")
            }
            existedIdList.add(id)
        }
        existedIdList.forEach { id -> accountRepository.deleteById(id) }
        return existedIdList.size
    }

    override fun upload(userUUID: String, uploadDto: UploadDto): UploadDto {
        val userEntity = userService.findOrCreate(userUUID)

        val dtoEntities = uploadDto.inputList.toEntityList(
            userEntity = userEntity,
            resolveMail = { email -> mailService.findOrCreate(email) },
            resolveType = { type -> typeService.findOrCreate(type) }
        )

        val accountsEntities = accountRepository.findAllByUserEntity(userEntity)
        val resultEntitiesList = (accountsEntities + dtoEntities).distinctBy { Pair(it.name, it.password) }
        val savedEntities = accountRepository.saveAll(resultEntitiesList)

        return UploadDto(
            count = abs(accountsEntities.size - resultEntitiesList.size),
            inputList = savedEntities.toDtoList()
        )
    }

    override fun upload(userUUID: String, uploadFileDto: UploadFileDto): Int {
        val userEntity = userService.findOrCreate(userUUID)
        val inputEntities = converter.convertToEntities(userEntity, uploadFileDto.json)
        when (uploadFileDto.type) {
            ImportType.IMPORT -> import(inputEntities)
            ImportType.REPLACE -> replace(userEntity, inputEntities)
        }
        return inputEntities.size
    }

    override fun replace(userUUID: String, uploadDto: UploadDto): UploadDto {
        val userEntity = userService.findOrCreate(userUUID)
        val accountEntities = uploadDto.inputList.toEntityList(
            userEntity = userEntity,
            resolveMail = { mailService.findOrCreate(it) },
            resolveType = { typeService.findOrCreate(it) }
        )

        accountRepository.deleteAllByUserEntity(userEntity)
        val savedEntities = accountRepository.saveAll(accountEntities)
        val savedDtoList = savedEntities.toDtoList()
        return UploadDto(
            count = savedDtoList.size,
            inputList = savedDtoList
        )
    }

    override fun import(inputList: List<AccountEntity>) {
        accountRepository.saveAll(inputList)
    }

    override fun replace(userEntity: UserEntity, inputList: List<AccountEntity>) {
        accountRepository.removeAllByUserEntity(userEntity)
        accountRepository.saveAll(inputList)
    }

}