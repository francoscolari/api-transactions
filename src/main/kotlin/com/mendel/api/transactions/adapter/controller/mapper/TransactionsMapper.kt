package com.mendel.api.transactions.adapter.controller.mapper

import com.mendel.api.transactions.adapter.controller.model.TransactionResponse
import com.mendel.api.transactions.adapter.controller.model.TransactionsRequest
import com.mendel.api.transactions.adapter.controller.model.TransactionsSumResponse
import com.mendel.api.transactions.domain.PreTransaction

fun Double.toTransactionsSumResponse(): TransactionsSumResponse = TransactionsSumResponse(sum = this)

fun TransactionsRequest.toPreTransactions(transactionId: Long): PreTransaction =
    PreTransaction(
        id = transactionId,
        type = type,
        amount = amount,
        parentId = parentId
    )

fun Long.toTransactionResponse(): TransactionResponse = TransactionResponse()
