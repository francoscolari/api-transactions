package com.mendel.api.transactions.adapter.db.config

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.adapter.db.read.TransactionsDbRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader {

    @Bean
    fun loadData(transactionsDbRepository: TransactionsDbRepository): CommandLineRunner {
        return CommandLineRunner {
            val transactions = listOf(
                TransactionEntity(id = 1, amount = 100.00, type = "cars"),
                TransactionEntity(
                    id = 2,
                    amount = 200.00,
                    type = "cars",
                    parent = TransactionEntity(id = 1, amount = 100.00, type = "cars")
                ),
                TransactionEntity(
                    id = 3,
                    amount = 300.00,
                    type = "bikes",
                    parent = TransactionEntity(id = 2, amount = 200.00, type = "cars")
                ),
                TransactionEntity(id = 4, amount = 300.00, type = "bikes")
            )

            transactionsDbRepository.saveAll(transactions)
        }
    }
}