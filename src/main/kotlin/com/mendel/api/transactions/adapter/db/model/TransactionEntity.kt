package com.mendel.api.transactions.adapter.db.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "TRANSACTION")
data class TransactionEntity(
    @Id
    val id: Long,
    val amount: Double,
    val type: String,
    val parentId: Long? = null,
)