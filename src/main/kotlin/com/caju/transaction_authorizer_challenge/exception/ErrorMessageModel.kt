package com.caju.transaction_authorizer_challenge.exception

data class ErrorMessageModel(
    val status: Int? = null,
    val message: String? = null,
    val timestamp: Long
)