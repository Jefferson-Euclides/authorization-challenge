package com.caju.transaction_authorizer_challenge.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(AccountNotFoundException::class, MerchantNotFoundException::class)
    fun handleEntityNotFoundException(ex: AccountNotFoundException): ResponseEntity<ErrorMessageModel> {
       val errorMessage = ErrorMessageModel(
           status = HttpStatus.NOT_FOUND.value(),
           message = ex.message,
           timestamp = Instant.now().toEpochMilli()
       )

        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorMessageModel> {
        val errorResponse = ErrorMessageModel(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "An unexpected error occurred",
            timestamp = Instant.now().toEpochMilli()
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}