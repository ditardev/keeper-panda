package com.micro.panda.appconfig.exceptions

import com.hadiyarajesh.spring_security_demo.app.exception.ResourceNotFoundException
import com.micro.panda.appconfig.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): ResponseEntity<ApiResponse.Error> {
        return ResponseEntity(
            ApiResponse.Error(status = false, message = e.message),
            e.httpStatus
        )
    }

}