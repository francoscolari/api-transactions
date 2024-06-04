package com.mendel.api.transactions.adapter.db

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TransactionsDbRepository : JpaRepository<TransactionEntity, Long> {
    fun findByType(type: String): List<TransactionEntity>
    override fun findById(transactionId: Long): Optional<TransactionEntity>
}