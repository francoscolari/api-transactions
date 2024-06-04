package com.mendel.api.transactions.adapter.controller.mapper

import com.mendel.api.transactions.adapter.controller.model.TransactionsSumResponse

fun Double.toTransactionsSumResponse(): TransactionsSumResponse = TransactionsSumResponse(sum = this)
