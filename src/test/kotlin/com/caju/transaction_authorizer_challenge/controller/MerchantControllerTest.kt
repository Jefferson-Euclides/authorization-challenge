package com.caju.transaction_authorizer_challenge.controller

import com.caju.transaction_authorizer_challenge.model.dto.MerchantDTO
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import com.caju.transaction_authorizer_challenge.service.MerchantService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MerchantController::class)
class MerchantControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var merchantService: MerchantService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should save merchant and return 201 Created`() {
        val merchantDtoRequest = MerchantDTO(
            name = "Padaria do teste",
            category = CategoryEnum.FOOD
        )

        val merchantDtoResponse = MerchantDTO(
            id = 1L,
            name = "Padaria do teste",
            category = CategoryEnum.FOOD
        )

        // Mock do serviço
        BDDMockito.given(merchantService.save(merchantDtoRequest)).willReturn(merchantDtoResponse)

        // Transformar o DTO em JSON
        val requestBody = objectMapper.writeValueAsString(merchantDtoRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/merchants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(merchantDtoResponse.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(merchantDtoResponse.category.category))
    }

    @Test
    fun `should return merchant by id as MerchantDTO`() {
        val merchantDTO = MerchantDTO(
            id = 1L,
            name = "Padaria do teste",
            category = CategoryEnum.FOOD
        )

        BDDMockito.given(merchantService.findById(1L)).willReturn(merchantDTO)

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/merchants/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(merchantDTO.category.category))
    }

}