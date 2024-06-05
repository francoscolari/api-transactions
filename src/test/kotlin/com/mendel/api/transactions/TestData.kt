package com.mendel.api.transactions

import com.mendel.api.transactions.TestConstants.Companion.AMOUNT
import com.mendel.api.transactions.TestConstants.Companion.TRANSACTION_ID
import com.mendel.api.transactions.TestConstants.Companion.TYPE
import com.mendel.api.transactions.adapter.controller.model.TransactionsRequest
import com.mendel.api.transactions.adapter.controller.model.TransactionsSumResponse
import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.PreTransaction
import com.mendel.api.transactions.domain.Transaction
import com.mendel.api.transactions.shared.error.ErrorHandler
import com.mendel.api.transactions.shared.error.provider.ErrorResponseProvider
import javax.servlet.http.HttpServletRequest

fun aTransactionsRequest() = TransactionsRequest(
    amount = 10.0,
    type = "operation",
    parentId = null
)

fun aPreTransaction() = PreTransaction(
    id = 12,
    amount = 10.0,
    type = "operation",
    parentId = null
)

fun aPreTransactionParent() = PreTransaction(
    id = 12,
    amount = 10.0,
    type = "operation",
    parentId = 11
)

fun aTransaction() = Transaction(
    id = TRANSACTION_ID,
    amount = AMOUNT,
    type = TYPE,
    parent = Transaction(
        id = TRANSACTION_ID,
        amount = AMOUNT,
        type = TYPE
    ),
    children = emptyList()
)

fun aTransactionEntity() = TransactionEntity(
    id = TRANSACTION_ID,
    amount = AMOUNT,
    type = TYPE,
    parent = TransactionEntity(
        id = TRANSACTION_ID,
        amount = AMOUNT,
        type = TYPE
    )
)

fun aTransactionsSumResponse() = TransactionsSumResponse(
    sum = 100.0,
)

fun aControllerAdvice() =
    ErrorHandler(
        errorResponseProvider = ErrorResponseProvider()
    )
