package com.caju.transaction_authorizer_challenge.mapper

import com.caju.transaction_authorizer_challenge.model.dto.MerchantDTO
import com.caju.transaction_authorizer_challenge.model.entity.MerchantEntity

fun MerchantDTO.toEntity() = MerchantEntity(
    name = this.name,
    category = this.category
)

fun MerchantEntity.toDTO() = MerchantDTO(
    id = this.id,
    name = this.name,
    category = this.category
)