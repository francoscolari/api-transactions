package com.mendel.api.transactions.application.port.out

import com.mendel.api.transactions.domain.Transaction

interface TransactionsOutPort {
    fun save(transaction: Transaction): Transaction
    fun findByType(type: String): List<Transaction>
    fun findById(transactionId: Long): List<Transaction>
}
