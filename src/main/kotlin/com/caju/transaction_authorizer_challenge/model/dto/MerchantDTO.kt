package com.caju.transaction_authorizer_challenge.model.dto

import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum

data class MerchantDTO(
    val id: Long = -1L,
    val name: String,
    val category: CategoryEnum,
)