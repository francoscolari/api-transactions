package com.mendel.api.transactions.adapter.controller

import com.mendel.api.transactions.TestConstants.Companion.TRANSACTION_ID
import com.mendel.api.transactions.TestConstants.Companion.TYPE
import com.mendel.api.transactions.aTransactionsRequest
import com.mendel.api.transactions.aTransactionsSumResponse
import com.mendel.api.transactions.adapter.controller.TransactionsController
import com.mendel.api.transactions.adapter.controller.mapper.toPreTransactions
import com.mendel.api.transactions.adapter.controller.mapper.toTransactionResponse
import com.mendel.api.transactions.application.port.`in`.TransactionsInPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class TransactionsControllerSpec : FeatureSpec({

    val transactionsInPort = mockk<TransactionsInPort>()
    val controller = TransactionsController(transactionsInPort)

    beforeEach { clearAllMocks() }

    feature("get transactions type") {
        scenario("should return success") {
            val type = TYPE
            val response = listOf(10L)
            every {
                transactionsInPort.find(type)
            } returns response

            controller.getTransactions(type) shouldBe response

            verify(exactly = 1) {
                transactionsInPort.find(type)
            }
        }

        scenario("should return error") {
            val type = TYPE

            every {
                transactionsInPort.find(type)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                controller.getTransactions(type)
            }

            verify(exactly = 1) {
                transactionsInPort.find(type)
            }
        }
    }

    feature("get sum transactions") {
        scenario("should return success") {

            val response = aTransactionsSumResponse()
            every {
                transactionsInPort.sum(TRANSACTION_ID)
            } returns 100.0

            controller.getTransactionsSum(TRANSACTION_ID) shouldBe response

            verify(exactly = 1) {
                transactionsInPort.sum(TRANSACTION_ID)
            }
        }

        scenario("should return error") {
            every {
                transactionsInPort.sum(TRANSACTION_ID)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                controller.getTransactionsSum(TRANSACTION_ID)
            }

            verify(exactly = 1) {
                transactionsInPort.sum(TRANSACTION_ID)
            }
        }
    }

    feature("put transactions") {
        scenario("should return success") {

            val request = aTransactionsRequest()
            val preTransaction = request.toPreTransactions(TRANSACTION_ID)
            val response = 100L
            every {
                transactionsInPort.save(preTransaction)
            } returns response

            controller.putTransactions(TRANSACTION_ID, request) shouldBe response.toTransactionResponse()

            verify(exactly = 1) {
                transactionsInPort.save(preTransaction)
            }
        }

        scenario("should return error") {
            val request = aTransactionsRequest()
            val preTransaction = request.toPreTransactions(TRANSACTION_ID)

            every {
                transactionsInPort.save(preTransaction)
            } throws NoSuchElementException()

            shouldThrow<NoSuchElementException> {
                controller.putTransactions(TRANSACTION_ID, request)
            }

            verify(exactly = 1) {
                transactionsInPort.save(preTransaction)
            }
        }
    }
})