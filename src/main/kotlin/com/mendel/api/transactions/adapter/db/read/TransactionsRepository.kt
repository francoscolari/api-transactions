package com.mendel.api.transactions.adapter.db.read

import com.mendel.api.transactions.adapter.db.mapper.toTransaction
import com.mendel.api.transactions.application.port.out.TransactionsOutPort
import com.mendel.api.transactions.domain.Transaction
import com.mendel.api.transactions.shared.log.CompanionLogger
import com.mendel.api.transactions.shared.log.benchmark
import org.springframework.stereotype.Component

@Component
class TransactionsRepository(
    private val transactionsDbRepository: TransactionsDbRepository
) : TransactionsOutPort {
    override fun findByType(type: String): List<Transaction> =
        log.benchmark("find by type") {
            transactionsDbRepository.findByType(type).map {
                it.toTransaction()
            }
        }

    override fun findById(transactionId: Long): Transaction =
        log.benchmark("find by id") {
            transactionsDbRepository.findById(transactionId).orElseThrow().toTransaction()
        }

    companion object : CompanionLogger()
}
