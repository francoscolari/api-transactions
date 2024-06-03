package com.mendel.api.transactions.adapter.db.read

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionsDbRepository : JpaRepository<TransactionEntity, Long>{
    fun findByType(type: String): List<TransactionEntity>
}
