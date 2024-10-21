package com.caju.transaction_authorizer_challenge.model.entity

import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "merchants")
data class MerchantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,
    val name: String,
    val category: CategoryEnum = CategoryEnum.FOOD,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updateAt: LocalDateTime = LocalDateTime.now(),
)
