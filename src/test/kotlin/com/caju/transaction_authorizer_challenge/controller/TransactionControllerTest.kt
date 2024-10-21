package com.caju.transaction_authorizer_challenge.controller

import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.dto.TransactionDTO
import com.caju.transaction_authorizer_challenge.model.dto.TransactionResponseDTO
import com.caju.transaction_authorizer_challenge.model.enums.FallbackEnum
import com.caju.transaction_authorizer_challenge.service.AccountService
import com.caju.transaction_authorizer_challenge.service.TransactionService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(TransactionController::class)
class TransactionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var transactionService: TransactionService

    private val objectMapper = jacksonObjectMapper()

    @ParameterizedTest
    @EnumSource(FallbackEnum::class)
    fun `should handle all the authorize fallback properly`(fallbackEnum: FallbackEnum) {
        val transactionResponseDTO = TransactionResponseDTO(
            code = FallbackEnum.APPROVED.fallback
        )

        val transactionDTO = TransactionDTO(
            account = "1",
            totalAmount = 10.0,
            mcc = "123",
            merchant = "Merchant Test"
        )

        // Mock do serviço
        BDDMockito.given(transactionService.authorize(transactionDTO)).willReturn(fallbackEnum.fallback)

        // Transformar o DTO em JSON
        val requestBody = objectMapper.writeValueAsString(transactionDTO)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(fallbackEnum.fallback))
    }

}