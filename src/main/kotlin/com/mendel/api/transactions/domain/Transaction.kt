package com.mendel.api.transactions.domain

data class Transaction(
    val id: Long,
    val amount: Double,
    val type: String,
    val parentId: Long?,
)