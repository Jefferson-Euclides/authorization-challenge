package com.caju.transaction_authorizer_challenge.controller

import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/accounts")
class AccountController(
    val accountService: AccountService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AccountController::class.java.name)
    }

    @Operation(summary = "Create a new account", description = "Create a new account to be used in a transaction")
    @ApiResponse(responseCode = "201", description = "Account created with success")
    @PostMapping(
        consumes = [APPLICATION_JSON_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    fun save(@RequestBody accountDTO: AccountDTO): ResponseEntity<AccountDTO> {
        logger.info("[AccountController.save] Request received")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(accountService.save(accountDTO))
    }

    @Operation(summary = "Find all accounts", description = "Returns all accounts registered in the system")
    @ApiResponse(responseCode = "200", description = "List of accounts or empty list")
    @GetMapping(
        produces = [APPLICATION_JSON_VALUE]
    )
    fun findAll(): ResponseEntity<List<AccountDTO>> {
        logger.info("[AccountController.findAll] Request received")
        return ResponseEntity.ok(
            accountService.findAll()
        )
    }

    @Operation(summary = "Find account by id", description = "Returns a account by id")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Account found with success"),
        ApiResponse(responseCode = "404", description = "Account not found", content = [Content()])
    ])
    @GetMapping(
        "/{id}",
        produces = [APPLICATION_JSON_VALUE]
    )
    fun findById(@PathVariable id: Long): ResponseEntity<AccountDTO> {
        logger.info("[AccountController.findById] Request received")
        return ResponseEntity.ok(accountService.findById(id))
    }
}