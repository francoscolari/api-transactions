package com.mendel.api.transactions.application.usecase

import com.mendel.api.transactions.application.port.`in`.TransactionsInPort
import com.mendel.api.transactions.application.port.out.TransactionsOutPort
import com.mendel.api.transactions.domain.Transaction
import org.springframework.stereotype.Component

@Component
class TransactionsUseCase(
    private val transactionsOutPort: TransactionsOutPort,
) : TransactionsInPort {
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
