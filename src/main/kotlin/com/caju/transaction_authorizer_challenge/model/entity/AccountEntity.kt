package com.caju.transaction_authorizer_challenge.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = -1L,
    val name: String,
    val documentNumber: String,
    var foodAmount: Double = 0.0,
    var mealAmount: Double = 0.0,
    var cashAmount: Double = 0.0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updateAt: LocalDateTime = LocalDateTime.now(),
    )
