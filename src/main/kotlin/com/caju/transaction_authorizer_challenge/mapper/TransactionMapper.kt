package com.caju.transaction_authorizer_challenge.mapper

import com.caju.transaction_authorizer_challenge.model.dto.TransactionDTO
import com.caju.transaction_authorizer_challenge.model.entity.TransactionEntity


fun TransactionDTO.toEntity() = TransactionEntity(
        accountId = this.account,
        amount = this.totalAmount,
        mcc = this.mcc,
        merchant = this.merchant
    )

