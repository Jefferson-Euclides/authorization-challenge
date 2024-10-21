package com.caju.transaction_authorizer_challenge.repository

import com.caju.transaction_authorizer_challenge.model.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantRepository : JpaRepository<MerchantEntity, Long> {

    fun findByName(name: String): MerchantEntity?

}