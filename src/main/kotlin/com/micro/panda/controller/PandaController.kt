package com.micro.panda.controller

import com.micro.panda.appconfig.model.ApiRequest
import com.micro.panda.appconfig.model.ApiResponse
import com.micro.panda.appconfig.model.RequestInfoDto
import com.micro.panda.service.PandaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
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
}