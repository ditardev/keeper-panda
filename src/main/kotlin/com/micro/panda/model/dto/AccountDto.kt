package com.micro.panda.model.dto

import com.micro.panda.model.Type

class AccountDto(
    val id: Int? = null,
    val userId: String? = null,
    val name: String? = null,
    val account: String? = null,
    val password: String? = null,
    val link: String? = null,
    val description: String? = null,
    val mail: String? = null,
    val type: Type? = null
) {}