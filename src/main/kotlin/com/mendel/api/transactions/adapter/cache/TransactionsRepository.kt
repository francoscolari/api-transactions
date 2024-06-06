package com.mendel.api.transactions.adapter.cache

import com.mendel.api.transactions.adapter.cache.mapper.toTransaction
import com.mendel.api.transactions.adapter.cache.mapper.toTransactionEntity
import com.mendel.api.transactions.application.port.out.TransactionsOutPort
import com.mendel.api.transactions.domain.Transaction
import com.mendel.api.transactions.shared.log.CompanionLogger
import com.mendel.api.transactions.shared.log.benchmark
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionsRepository(
    private val cache: InMemoryRepository
) : TransactionsOutPort {

    @Transactional
    override fun save(transaction: Transaction): Transaction =
        log.benchmark("find by type") {
            cache.set(transaction.id, transaction.toTransactionEntity()).toTransaction()
                .log { info("save response: {}", it) }
        }

    override fun findByType(type: String): List<Transaction> =
        log.benchmark("find by type") {
            cache.findByType(type).map {
                it.toTransaction()
            }.log { info("find by type response: {}", it) }
        }

    override fun findById(transactionId: Long): List<Transaction> =
        log.benchmark("find by id") {
            val transactionlist = cache.findById(transactionId)
            if (transactionlist != null) {
                return transactionlist.map { it.toTransaction() }
            } else {
                throw NoSuchElementException("No such element found")
            }
        }

    companion object : CompanionLogger()
}
