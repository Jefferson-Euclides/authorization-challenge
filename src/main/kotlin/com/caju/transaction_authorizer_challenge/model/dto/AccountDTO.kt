package com.caju.transaction_authorizer_challenge.model.dto

data class AccountDTO(
    val id: Long? = -1L,
    val name: String,
    val documentNumber: String,
    var foodAmount: Double = 0.0,
    var mealAmount: Double = 0.0,
    var cashAmount: Double = 0.0
)
