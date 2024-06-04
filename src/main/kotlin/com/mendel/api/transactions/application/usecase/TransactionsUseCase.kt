package com.mendel.api.transactions.application.usecase

import com.mendel.api.transactions.application.port.`in`.TransactionsInPort
import com.mendel.api.transactions.application.port.out.TransactionsOutPort
import com.mendel.api.transactions.domain.PreTransaction
import com.mendel.api.transactions.domain.Transaction
import com.mendel.api.transactions.domain.mapper.toTransaction
import org.springframework.stereotype.Component

@Component
class TransactionsUseCase(
    private val transactionsOutPort: TransactionsOutPort,
) : TransactionsInPort {

    override fun createOrUpdate(preTransaction: PreTransaction): Long {
        val parentTransaction = preTransaction.parentId?.let { transactionsOutPort.findById(it) }
        return transactionsOutPort.createOrUpdate(preTransaction.toTransaction(parentTransaction)).id
    }

    override fun find(type: String): List<Long> = transactionsOutPort.findByType(type).map { it.id }

    override fun sum(transactionId: Long): Double =
        transactionsOutPort.findById(transactionId).calculateSum()

    private fun Transaction.calculateSum(): Double {
        var sum = this.amount
        if (!this.children.isNullOrEmpty()) {
            sum += this.children.sumOf { it.calculateSum() }
        }
        return sum
    }
}
