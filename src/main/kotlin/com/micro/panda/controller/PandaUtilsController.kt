package com.micro.panda.controller

import com.micro.panda.appconfig.model.ApiResponse
import com.micro.panda.service.UtilService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${server.endpoint.main}/utils")
class PandaUtilsController(
    val pandaUtils: UtilService,
) {

    @GetMapping("/generate")
    fun generate(): ResponseEntity<*>? {
        return ResponseEntity.ok(ApiResponse.Success(true, pandaUtils.generatePassword()))
    }
}