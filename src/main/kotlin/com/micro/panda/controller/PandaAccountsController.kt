package com.micro.panda.controller

import com.micro.panda.appconfig.model.ApiRequest
import com.micro.panda.appconfig.model.ApiResponse
import com.micro.panda.appconfig.model.RequestInfoDto
import com.micro.panda.model.dto.AccountDto
import com.micro.panda.model.UploadFileDto
import com.micro.panda.service.PandaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${server.endpoint.main}/accounts")
class PandaAccountsController(
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

    @PostMapping("/delete")
    fun delete(@RequestBody request: ApiRequest<List<Long>>): ResponseEntity<*>? {
        val data = pandaService.delete(request.userUUID, request.data)
        return ResponseEntity.ok(ApiResponse.Success(true, data))
    }

    @PostMapping("/import")
    fun import(@RequestBody request: ApiRequest<UploadFileDto>): ResponseEntity<*>? {
        return ResponseEntity.ok(
            ApiResponse.Success(
                true,
                pandaService.upload(request.userUUID, request.data))
        )
    }

}