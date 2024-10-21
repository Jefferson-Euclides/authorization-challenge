package com.caju.transaction_authorizer_challenge.service

import com.caju.transaction_authorizer_challenge.controller.MerchantController
import com.caju.transaction_authorizer_challenge.exception.MerchantNotFoundException
import com.caju.transaction_authorizer_challenge.mapper.toDTO
import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.dto.MerchantDTO
import com.caju.transaction_authorizer_challenge.model.entity.MerchantEntity
import com.caju.transaction_authorizer_challenge.repository.MerchantRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MerchantService(
    val merchantRepository: MerchantRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(MerchantService::class.java.name)
    }
    fun save(merchantDTO: MerchantDTO): MerchantDTO {
        return merchantRepository.save(merchantDTO.toEntity()).toDTO()
    }

    fun findByName(merchantName: String): MerchantDTO? {
        return merchantRepository.findByName(merchantName)?.toDTO()
    }

    fun findById(id: Long): MerchantDTO? {
        return merchantRepository.findById(id)
            .orElseThrow {
                MerchantNotFoundException("Merchant not found")
            }?.toDTO()
    }

}