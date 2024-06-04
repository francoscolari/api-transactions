package com.mendel.api.transactions.adapter.controller.model

import org.springframework.http.HttpStatus

data class TransactionResponse(
    val status: HttpStatus = HttpStatus.OK
)
