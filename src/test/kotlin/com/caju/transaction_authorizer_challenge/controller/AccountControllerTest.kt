package com.caju.transaction_authorizer_challenge.controller

import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.entity.AccountEntity
import com.caju.transaction_authorizer_challenge.service.AccountService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.random.Random

@WebMvcTest(AccountController::class)
class AccountControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var accountService: AccountService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should save account and return 201 Created`() {
        val accountDTO = AccountDTO(
            id = -1L,
            foodAmount = 100.0,
            mealAmount = 100.0,
            cashAmount = 100.0,
            documentNumber = "12345678444",
            name = "Test Account"
        )

        given(accountService.save(accountDTO)).willReturn(accountDTO)
        val requestBody = objectMapper.writeValueAsString(accountDTO)

        mockMvc.perform(post("/v1/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.foodAmount").value(100.0))
            .andExpect(jsonPath("$.mealAmount").value(100.0))
            .andExpect(jsonPath("$.cashAmount").value(100.0))
    }

    @Test
    fun `should return all accounts as AccountDTO list`() {
        val accounts = listOf(
            AccountDTO(
                id = 1L,
                foodAmount = 100.0,
                mealAmount = 100.0,
                cashAmount = 100.0,
                documentNumber = "12345678444",
                name = "Test Account 1"
            ),
            AccountDTO(
                id = 2L,
                foodAmount = 200.0,
                mealAmount = 200.0,
                cashAmount = 200.0,
                documentNumber = "12345678444",
                name = "Test Account 2"
            )
        )

        given(accountService.findAll()).willReturn(accounts)

        mockMvc.perform(get("/v1/accounts"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(accounts.size))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2))
    }

    @Test
    fun `should return account by id as AccountDTO`() {
        val accountDTO = AccountDTO(
            id = 1L,
            foodAmount = 100.0,
            mealAmount = 100.0,
            cashAmount = 100.0,
            documentNumber = "12345678444",
            name = "Test Account"
        )

        given(accountService.findById(1L)).willReturn(accountDTO)

        mockMvc.perform(get("/v1/accounts/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.foodAmount").value(100.0))
            .andExpect(jsonPath("$.mealAmount").value(100.0))
            .andExpect(jsonPath("$.cashAmount").value(100.0))
    }
}
