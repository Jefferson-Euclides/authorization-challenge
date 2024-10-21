package com.caju.transaction_authorizer_challenge.model.dto

data class TransactionDTO(
    val account: String,
    val totalAmount: Double,
    val mcc: String,
    val merchant: String,
    )
