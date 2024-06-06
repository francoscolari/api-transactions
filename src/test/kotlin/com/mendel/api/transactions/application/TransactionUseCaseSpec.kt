package com.mendel.api.transactions.application

import com.mendel.api.transactions.TestConstants.Companion.TRANSACTION_ID
import com.mendel.api.transactions.TestConstants.Companion.TYPE
import com.mendel.api.transactions.aPreTransaction
import com.mendel.api.transactions.aTransaction
import com.mendel.api.transactions.application.port.out.TransactionsOutPort
import com.mendel.api.transactions.application.usecase.TransactionsUseCase
import com.mendel.api.transactions.domain.mapper.toTransaction
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class TransactionUseCaseSpec : FeatureSpec({

    val transactionsOutPort = mockk<TransactionsOutPort>()
    val useCase = TransactionsUseCase(transactionsOutPort)

    beforeEach { clearAllMocks() }

    feature("find transactions type") {
        scenario("should return success") {
            val type = TYPE
            val response = listOf(10L)
            every {
                transactionsOutPort.findByType(type)
            } returns listOf(aTransaction())

            useCase.find(type) shouldBe response

            verify(exactly = 1) {
                transactionsOutPort.findByType(type)
            }
        }

        scenario("should return error") {
            val type = TYPE

            every {
                transactionsOutPort.findByType(type)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                useCase.find(type)
            }

            verify(exactly = 1) {
                transactionsOutPort.findByType(type)
            }
        }
    }

    feature("sum transactions") {
        scenario("should return success") {

            every {
                transactionsOutPort.findById(TRANSACTION_ID)
            } returns listOf(aTransaction())

            useCase.sum(TRANSACTION_ID) shouldBe 200000.0

            verify(exactly = 1) {
                transactionsOutPort.findById(TRANSACTION_ID)
            }
        }

        scenario("should return error") {
            every {
                transactionsOutPort.findById(TRANSACTION_ID)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                useCase.sum(TRANSACTION_ID)
            }

            verify(exactly = 1) {
                transactionsOutPort.findById(TRANSACTION_ID)
            }
        }
    }

    feature("save transactions") {
        scenario("should return success without parent") {

            val preTransaction = aPreTransaction()
            val transaction = preTransaction.toTransaction()

            every {
                transactionsOutPort.save(transaction)
            } returns aTransaction()

            useCase.save(preTransaction) shouldBe TRANSACTION_ID

            verify(exactly = 1) {
                transactionsOutPort.save(transaction)
            }
        }

        scenario("should return error") {
            val preTransaction = aPreTransaction()
            val transaction = preTransaction.toTransaction()

            every {
                transactionsOutPort.save(transaction)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                useCase.save(preTransaction)
            }

            verify(exactly = 1) {
                transactionsOutPort.save(transaction)
            }
        }
    }
})
