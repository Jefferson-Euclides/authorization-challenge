package com.caju.transaction_authorizer_challenge.service

import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import com.caju.transaction_authorizer_challenge.model.enums.FallbackEnum
import com.caju.transaction_authorizer_challenge.model.dto.TransactionDTO
import com.caju.transaction_authorizer_challenge.repository.TransactionRepository
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TransactionService(
    val redissonClient: RedissonClient,
    val accountService: AccountService,
    val merchantService: MerchantService,
    val transactionRepository: TransactionRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(TransactionService::class.java.name)
    }

    fun authorize(transactionDTO: TransactionDTO): String {
        val lock = redissonClient.getLock("account:${transactionDTO.account}")
        lock.lock()

        return try {
            val account = accountService.findById(transactionDTO.account.toLong())
            logger.info(
                "[TransactionService.authorize] Account found with id: {}",
                account.id
            )

            val category = resolveCategory(transactionDTO)
            logger.info(
                "[TransactionService.authorize] Transaction category decided: {}",
                category
            )

            val totalAmount = transactionDTO.totalAmount

            transactionRepository.save(transactionDTO.toEntity())
            logger.info(
                "[TransactionService.authorize] Transaction saved."
            )

            return accountService.debit(account, category, totalAmount).fallback
        } catch (exception: Exception) {
            logger.error("[TransactionService.authorize] Transaction authorization failed: ${exception.message}", exception)
            FallbackEnum.ERROR.fallback
        } finally {
            lock.unlock()
        }
    }

    private fun resolveCategory(transactionDTO: TransactionDTO) =
        (merchantService.findByName(transactionDTO.merchant)?.category
            ?: mccToCategory(transactionDTO.mcc))

    private fun mccToCategory(mcc: String): CategoryEnum {
        return when (mcc) {
            "5411", "5412" -> CategoryEnum.FOOD
            "5811", "5812" -> CategoryEnum.MEAL
            else -> CategoryEnum.CASH
        }
    }
}
