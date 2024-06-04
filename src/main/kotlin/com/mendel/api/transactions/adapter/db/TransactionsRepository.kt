package com.mendel.api.transactions.adapter.db

import com.mendel.api.transactions.adapter.db.mapper.toTransaction
import com.mendel.api.transactions.adapter.db.mapper.toTransactionEntity
import com.mendel.api.transactions.application.port.out.TransactionsOutPort
import com.mendel.api.transactions.domain.Transaction
import com.mendel.api.transactions.shared.log.CompanionLogger
import com.mendel.api.transactions.shared.log.benchmark
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionsRepository(
    private val transactionsDbRepository: TransactionsDbRepository
) : TransactionsOutPort {

    @Transactional
    override fun save(transaction: Transaction): Transaction =
        log.benchmark("find by type") {
            transactionsDbRepository.save(transaction.toTransactionEntity()).toTransaction()
                .log { info("save response: {}", it) }
        }

    override fun findByType(type: String): List<Transaction> =
        log.benchmark("find by type") {
            transactionsDbRepository.findByType(type).map {
                it.toTransaction()
            }.log { info("find by type response: {}", it) }
        }

    override fun findById(transactionId: Long): Transaction =
        log.benchmark("find by id") {
            transactionsDbRepository.findById(transactionId).orElseThrow().toTransaction()
                .log { info("find by id response: {}", it) }
        }

    companion object : CompanionLogger()
}
