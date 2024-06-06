package com.mendel.api.transactions.adapter.cache

import com.mendel.api.transactions.TestConstants
import com.mendel.api.transactions.TestConstants.Companion.TRANSACTION_ID
import com.mendel.api.transactions.aTransaction
import com.mendel.api.transactions.aTransactionEntity
import com.mendel.api.transactions.adapter.cache.mapper.toTransactionEntity
import com.mendel.api.transactions.adapter.cache.model.TransactionEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.NoSuchElementException

class TransactionsRepositorySpec : FeatureSpec({

    val cache = mockk<InMemoryRepository>()
    val repository = TransactionsRepository(cache)

    beforeEach { clearAllMocks() }

    feature("find transactions type") {
        scenario("should return success") {
            val type = TestConstants.TYPE
            val response = listOf(aTransaction())
            every {
                cache.findByType(type)
            } returns listOf(aTransactionEntity())

            repository.findByType(type) shouldBe response

            verify(exactly = 1) {
                cache.findByType(type)
            }
        }

        scenario("should return error") {
            val type = TestConstants.TYPE

            every {
                cache.findByType(type)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                repository.findByType(type)
            }

            verify(exactly = 1) {
                cache.findByType(type)
            }
        }
    }

    feature("findById transactions") {
        scenario("should return success") {
            val transactionEntity: TransactionEntity = aTransactionEntity()

            every {
                cache.findById(TRANSACTION_ID)
            } returns listOf(transactionEntity)

            repository.findById(TRANSACTION_ID) shouldBe listOf(aTransaction())

            verify(exactly = 1) {
                cache.findById(TRANSACTION_ID)
            }
        }

        scenario("should return error") {

            every {
                cache.findById(TRANSACTION_ID)
            } returns null

            shouldThrow<NoSuchElementException> {
                repository.findById(TRANSACTION_ID)
            }

            verify(exactly = 1) {
                cache.findById(TRANSACTION_ID)
            }
        }
    }

    feature("save transactions") {
        scenario("should return success") {
            val transaction = aTransaction()
            val transactionEntity = transaction.toTransactionEntity()

            every {
                cache.set(transaction.id, transactionEntity)
            } returns transactionEntity

            repository.save(transaction) shouldBe transaction

            verify(exactly = 1) {
                cache.set(transaction.id, transactionEntity)
            }
        }

        scenario("should return error") {
            val transaction = aTransaction()
            val transactionEntity = transaction.toTransactionEntity()

            every {
                cache.set(transaction.id, transactionEntity)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                repository.save(transaction)
            }

            verify(exactly = 1) {
                cache.set(transaction.id, transactionEntity)
            }
        }
    }
})
