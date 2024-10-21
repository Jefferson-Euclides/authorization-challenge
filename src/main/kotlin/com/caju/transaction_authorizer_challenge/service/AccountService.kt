package com.caju.transaction_authorizer_challenge.service

import com.caju.transaction_authorizer_challenge.exception.AccountNotFoundException
import com.caju.transaction_authorizer_challenge.mapper.toDTO
import com.caju.transaction_authorizer_challenge.mapper.toEntity
import com.caju.transaction_authorizer_challenge.model.dto.AccountDTO
import com.caju.transaction_authorizer_challenge.model.enums.CategoryEnum
import com.caju.transaction_authorizer_challenge.model.enums.FallbackEnum
import com.caju.transaction_authorizer_challenge.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AccountService(
    val accountRepository: AccountRepository
) {
    companion object {
        private val logger = LoggerFactory.getLogger(AccountService::class.java.name)
    }

    fun save(accountDTO: AccountDTO): AccountDTO {
        return accountRepository.save(accountDTO.toEntity()).toDTO()
    }

    fun findAll(): List<AccountDTO> {
        return accountRepository.findAll().map {
            it.toDTO()
        }
    }

    fun findById(id: Long): AccountDTO {
        return accountRepository.findById(id)
            .orElseThrow{
                AccountNotFoundException("Account not found")
            }.toDTO()
    }

    fun debit(account: AccountDTO, category: CategoryEnum, totalAmount: Double): FallbackEnum {
        when {
            hasSufficientBalance(category, totalAmount, account) -> {
                logger.info("[AccountService.debit] Sufficient balance. Starting to update account balance")
                updateBalance(category, totalAmount, account)
            }
            category != CategoryEnum.CASH && account.cashAmount >= totalAmount -> {
                logger.info(
                    "[AccountService.debit] Category:{} balance is not sufficient. Starting to update CASH balance",
                    category
                )
                updateBalance(CategoryEnum.CASH, totalAmount, account)
            }

            else -> {
                logger.info("[AccountService.debit] Insufficient balance. Rejecting transaction ")
                return FallbackEnum.REJECTED
            }
        }

        this.save(account)

        logger.info("[AccountService.debit] Approving transaction")
        return FallbackEnum.APPROVED
    }

    private fun updateBalance(category: CategoryEnum, totalAmount: Double, account: AccountDTO) {
        when (category) {
            CategoryEnum.FOOD -> account.foodAmount = adjustBalance(account.foodAmount, totalAmount)
            CategoryEnum.MEAL -> account.mealAmount = adjustBalance(account.mealAmount, totalAmount)
            CategoryEnum.CASH -> account.cashAmount = adjustBalance(account.cashAmount, totalAmount)
        }
    }

    private fun adjustBalance(currentAmount: Double, totalAmount: Double): Double {
        return (currentAmount - totalAmount).coerceAtLeast(0.0)
    }

    fun hasSufficientBalance(category: CategoryEnum, totalAmount: Double, account: AccountDTO): Boolean {
        return when (category) {
            CategoryEnum.FOOD -> account.foodAmount >= totalAmount
            CategoryEnum.MEAL -> account.mealAmount >= totalAmount
            CategoryEnum.CASH -> account.cashAmount >= totalAmount
        }
    }
}
