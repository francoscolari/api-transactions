package com.mendel.api.transactions.adapter.db

import com.mendel.api.transactions.TestConstants
import com.mendel.api.transactions.TestConstants.Companion.TRANSACTION_ID
import com.mendel.api.transactions.aTransaction
import com.mendel.api.transactions.aTransactionEntity
import com.mendel.api.transactions.adapter.db.mapper.toTransactionEntity
import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.Optional
import kotlin.NoSuchElementException

class TransactionsRepositorySpec : FeatureSpec({

    val transactionsDbRepository = mockk<TransactionsDbRepository>()
    val repository = TransactionsRepository(transactionsDbRepository)

    beforeEach { clearAllMocks() }

    feature("find transactions type") {
        scenario("should return success") {
            val type = TestConstants.TYPE
            val response = listOf(aTransaction())
            every {
                transactionsDbRepository.findByType(type)
            } returns listOf(aTransactionEntity())

            repository.findByType(type) shouldBe response

            verify(exactly = 1) {
                transactionsDbRepository.findByType(type)
            }
        }

        scenario("should return error") {
            val type = TestConstants.TYPE

            every {
                transactionsDbRepository.findByType(type)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                repository.findByType(type)
            }

            verify(exactly = 1) {
                transactionsDbRepository.findByType(type)
            }
        }
    }

    feature("findById transactions") {
        scenario("should return success") {
            val optionalTransactionEntity: Optional<TransactionEntity> = Optional.of(aTransactionEntity())

            every {
                transactionsDbRepository.findById(TRANSACTION_ID)
            } returns optionalTransactionEntity

            repository.findById(TRANSACTION_ID) shouldBe aTransaction()

            verify(exactly = 1) {
                transactionsDbRepository.findById(TRANSACTION_ID)
            }
        }

        scenario("should return error") {

            every {
                transactionsDbRepository.findById(TRANSACTION_ID)
            } returns Optional.empty()

            shouldThrow<NoSuchElementException> {
                repository.findById(TRANSACTION_ID)
            }

            verify(exactly = 1) {
                transactionsDbRepository.findById(TRANSACTION_ID)
            }
        }
    }

    feature("save transactions") {
        scenario("should return success") {
            val transaction = aTransaction()
            val transactionEntity = transaction.toTransactionEntity()

            every {
                transactionsDbRepository.save(transactionEntity)
            } returns transactionEntity

            repository.save(transaction) shouldBe transaction

            verify(exactly = 1) {
                transactionsDbRepository.save(transaction.toTransactionEntity())
            }
        }

        scenario("should return error") {
            val transaction = aTransaction()
            val transactionEntity = transaction.toTransactionEntity()

            every {
                transactionsDbRepository.save(transactionEntity)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                repository.save(transaction)
            }

            verify(exactly = 1) {
                transactionsDbRepository.save(transactionEntity)
            }
        }
    }
})
