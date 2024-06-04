package com.mendel.api.transactions.adapter.db.config

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.adapter.db.TransactionsDbRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader {

    @Bean
    fun loadData(transactionsDbRepository: TransactionsDbRepository): CommandLineRunner {
        return CommandLineRunner {
            val t1 = TransactionEntity(id = 10, amount = 5000.0, type = "cars", parent = null)
            val t2 = TransactionEntity(
                id = 11,
                amount = 10000.0,
                type = "shopping",
                parent = t1
            )
            val t3 = TransactionEntity(
                id = 12,
                amount = 5000.00,
                type = "shopping",
                parent = t2
            )

            val transactions = listOf(
                t1, t2, t3
            )

            transactionsDbRepository.saveAll(transactions)
        }
    }
}
