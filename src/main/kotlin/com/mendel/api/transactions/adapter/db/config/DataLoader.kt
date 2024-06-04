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
            val transactions = listOf(
                TransactionEntity(id = 10, amount = 5000.0, type = "cars"),
                TransactionEntity(
                    id = 11,
                    amount = 10000.0,
                    type = "shopping",
                    parent = TransactionEntity(id = 10, amount = 5000.0, type = "cars")
                ),
                TransactionEntity(
                    id = 12,
                    amount = 5000.00,
                    type = "shopping",
                    parent = TransactionEntity(id = 11, amount = 10000.0, type = "shopping")
                ),
                TransactionEntity(
                    id = 13,
                    amount = 5.00,
                    type = "new",
                    parent = TransactionEntity(id = 12, amount = 5000.0, type = "shopping")
                ),
            )

            transactionsDbRepository.saveAll(transactions)
        }
    }
}
