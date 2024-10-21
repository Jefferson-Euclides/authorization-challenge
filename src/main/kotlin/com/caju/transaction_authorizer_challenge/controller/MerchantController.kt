package com.caju.transaction_authorizer_challenge.controller

import com.caju.transaction_authorizer_challenge.mapper.toDTO
import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.dto.MerchantDTO
import com.caju.transaction_authorizer_challenge.service.MerchantService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/merchants")
class MerchantController(
    val merchantService: MerchantService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(MerchantController::class.java.name)
    }

    @Operation(summary = "Create new merchant", description = "Create a new merchant to be used in a transaction")
    @ApiResponse(responseCode = "202", description = "Merchant created with success")
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(@RequestBody merchantDTO: MerchantDTO) : ResponseEntity<MerchantDTO> {
        logger.info("[MerchantController.save] Request received")
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(merchantService.save(merchantDTO))
    }

    @Operation(summary = "Find merchant by id", description = "Returns a merchant by id")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Merchant found with success"),
        ApiResponse(responseCode = "404", description = "Merchant not found", content = [Content()])
    ])
    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findById(@PathVariable id: Long): ResponseEntity<MerchantDTO> {
        logger.info("[MerchantController.findById] Request received")
        return ResponseEntity.ok(merchantService.findById(id))
    }
}