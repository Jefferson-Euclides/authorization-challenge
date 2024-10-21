package com.caju.transaction_authorizer_challenge.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long = -1,
    val accountId: String,
    val amount: Double,
    val merchant: String,
    val mcc: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
