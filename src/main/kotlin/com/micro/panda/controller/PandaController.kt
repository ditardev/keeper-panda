package com.micro.panda.controller

import com.micro.panda.appconfig.model.ApiRequest
import com.micro.panda.appconfig.model.ApiResponse
import com.micro.panda.appconfig.model.RequestInfoDto
import com.micro.panda.model.dto.AccountDto
import com.micro.panda.service.PandaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${server.endpoint.main}")
class PandaController(
    private val pandaService: PandaService
) {

    @PostMapping("/all")
    fun selectAll(@RequestBody request: ApiRequest<RequestInfoDto>): ResponseEntity<*>? {
        val data = pandaService.selectAll(request.userUUID)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/create")
    fun create(@RequestBody request: ApiRequest<AccountDto>): ResponseEntity<*>? {
        val data = pandaService.create(request.userUUID, request.data)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PutMapping("/update")
    fun update(@RequestBody request: ApiRequest<AccountDto>): ResponseEntity<*>? {
        val data = pandaService.update(request.userUUID, request.data)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody request: ApiRequest<List<Long>>): ResponseEntity<*>? {
        val data = pandaService.delete(request.userUUID, request.data)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }
}