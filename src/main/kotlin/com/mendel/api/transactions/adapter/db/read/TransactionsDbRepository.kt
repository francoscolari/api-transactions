package com.mendel.api.transactions.adapter.db.read

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionsDbRepository : JpaRepository<TransactionEntity, Long>{
    fun findByType(type: String): List<TransactionEntity>
    override fun findById(transactionId: Long): Optional<TransactionEntity>
}
