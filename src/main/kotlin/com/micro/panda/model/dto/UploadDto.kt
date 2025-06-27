package com.micro.panda.model.dto

data class UploadDto(
    val count: Int? = 0,
    val inputList: List<AccountDto>,
)