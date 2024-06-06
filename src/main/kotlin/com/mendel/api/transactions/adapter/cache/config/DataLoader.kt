package com.mendel.api.transactions.adapter.cache.config

import com.mendel.api.transactions.adapter.cache.InMemoryRepository
import com.mendel.api.transactions.adapter.cache.model.TransactionEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader(
    private val cache: InMemoryRepository
) {

    @Bean
    fun loadData(): CommandLineRunner {
        return CommandLineRunner {
            val t1 = TransactionEntity(id = 10, amount = 5000.0, type = "cars", parentId = null)
            val t2 = TransactionEntity(
                id = 11,
                amount = 10000.0,
                type = "shopping",
                parentId = 10
            )
            val t3 = TransactionEntity(
                id = 12,
                amount = 5000.00,
                type = "shopping",
                parentId = 11
            )

            cache.set(t1.id, t1)
            cache.set(t2.id, t2)
            cache.set(t3.id, t3)
        }
    }
}
