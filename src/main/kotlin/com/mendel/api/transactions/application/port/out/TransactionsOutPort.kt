package com.mendel.api.transactions.application.port.out

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction

interface TransactionsOutPort {
    fun findByType(type: String): List<Transaction>
    fun findById(transactionId: Long): Transaction
}