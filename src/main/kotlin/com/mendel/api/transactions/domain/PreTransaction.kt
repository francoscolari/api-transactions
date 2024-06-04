package com.mendel.api.transactions.domain

data class PreTransaction(
    val id: Long,
    val amount: Double,
    val type: String,
    var parentId: Long?,
)
