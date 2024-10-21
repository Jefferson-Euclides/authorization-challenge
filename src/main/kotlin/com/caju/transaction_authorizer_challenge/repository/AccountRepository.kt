package com.caju.transaction_authorizer_challenge.repository

import com.caju.transaction_authorizer_challenge.model.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long> {
}