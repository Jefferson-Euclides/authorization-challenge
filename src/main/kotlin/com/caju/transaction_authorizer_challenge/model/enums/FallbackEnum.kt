package com.caju.transaction_authorizer_challenge.model.enums

enum class FallbackEnum(val fallback: String) {
    APPROVED("00"),
    REJECTED("51"),
    ERROR("07")
}