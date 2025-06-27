package com.micro.panda.appconfig.model

data class ApiRequest<T>(
    val userUUID: String,
    val data: T
)