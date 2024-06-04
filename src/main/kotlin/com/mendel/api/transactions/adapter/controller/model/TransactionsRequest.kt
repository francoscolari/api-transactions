package com.mendel.api.transactions.adapter.controller.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionsRequest(
    val amount: Double,
    val type: String,
    @JsonProperty("parent_id")
    val parentId: Long?
)
