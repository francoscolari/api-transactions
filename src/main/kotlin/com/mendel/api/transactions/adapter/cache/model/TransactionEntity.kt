package com.mendel.api.transactions.adapter.cache.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "TRANSACTION")
data class TransactionEntity(
    @Id
    val id: Long,
    val amount: Double,
    val type: String,
    val parentId: Long?

) {
    override fun toString(): String {
        return "TransactionEntity(id=$id, amount=$amount, type='$type', parent_id=$parentId)"
    }
}
