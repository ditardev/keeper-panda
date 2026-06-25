package com.micro.panda.service

import com.micro.panda.model.UploadFileDto
import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.dto.UploadDto
import com.micro.panda.model.entity.AccountEntity
import com.micro.panda.model.entity.UserEntity

interface PandaService {

    fun selectAll(userUUID: String?): List<AccountDto>?
    //    fun selectByPage(userUUID: String, pageNumber: Int, pageSize: Int): List<AccountDto>?
    fun create(userUUID: String, accountDto: AccountDto): AccountDto
    fun update(userUUID: String, accountDto: AccountDto): AccountDto
    fun delete(userUUID: String, idList: List<Long>): Int
    fun upload(userUUID: String, uploadDto: UploadDto): UploadDto
    fun replace(userUUID: String, uploadDto: UploadDto): UploadDto
    fun upload(userUUID: String, uploadFileDto: UploadFileDto): Int
    fun import(inputList: List<AccountEntity>)
    fun replace(userEntity: UserEntity, inputList: List<AccountEntity>)

}