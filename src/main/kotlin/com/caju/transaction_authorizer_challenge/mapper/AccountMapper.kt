package com.caju.transaction_authorizer_challenge.mapper

import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.entity.AccountEntity

fun AccountDTO.toEntity() = AccountEntity(
    id = this.id,
    name = this.name,
    documentNumber = this.documentNumber,
    foodAmount = this.foodAmount,
    mealAmount = this.mealAmount,
    cashAmount = this.cashAmount
)

fun AccountEntity.toDTO() = AccountDTO(
    id = this.id,
    name = this.name,
    documentNumber = this.documentNumber,
    foodAmount = this.foodAmount,
    mealAmount = this.mealAmount,
    cashAmount = this.cashAmount
)