package com.caju.transaction_authorizer_challenge.repository

import com.caju.transaction_authorizer_challenge.model.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<TransactionEntity, Long> {
}