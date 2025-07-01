package com.micro.panda.model.dto

import java.sql.Timestamp

data class AccountDto(
    val id: Long? = null,
    val name: String? = null,
    val account: String? = null,
    val password: String? = null,
    val link: String? = null,
    val description: String? = null,
    val email: String? = null,
    val type: String? = null,
    val updated: Timestamp? = Timestamp(System.currentTimeMillis()),
)