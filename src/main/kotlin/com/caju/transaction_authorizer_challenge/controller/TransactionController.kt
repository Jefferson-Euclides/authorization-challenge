package com.caju.transaction_authorizer_challenge.controller

import com.caju.transaction_authorizer_challenge.model.dto.TransactionDTO
import com.caju.transaction_authorizer_challenge.model.dto.TransactionResponseDTO
import com.caju.transaction_authorizer_challenge.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/transactions")
class TransactionController(
    val transactionService: TransactionService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(TransactionController::class.java.name)
    }
    @Operation(summary = "Create a new transaction", description = "Create a new transaction to be processed, " +
            "it will authorize if the user account have sufficient balance in the category requested.")
    @ApiResponse(
        responseCode = "200",
        description = "Successful transaction response",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = TransactionResponseDTO::class),
            examples = [
                ExampleObject(
                    name = "Approved",
                    summary = "Transaction approved",
                    value = """{"code": "00"}"""
                ),
                ExampleObject(
                    name = "Rejected",
                    summary = "Transaction rejected due to insufficient balance",
                    value = """{"code": "51"}"""
                ),
                ExampleObject(
                    name = "Error",
                    summary = "Transaction failed due to an error",
                    value = """{"code": "07"}"""
                )
            ]
        )])
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun authorize(
        @RequestBody transactionDTO: TransactionDTO
    ): ResponseEntity<TransactionResponseDTO> {
       logger.info("[TransactionController.authorize] Request received")
        return ResponseEntity.ok(
            TransactionResponseDTO(
                transactionService.authorize(transactionDTO)
            )
        )
    }

}