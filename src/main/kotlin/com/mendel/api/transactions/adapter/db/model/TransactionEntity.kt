package com.mendel.api.transactions.adapter.db.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity(name = "TRANSACTION")
data class TransactionEntity(
    @Id
    val id: Long,
    val amount: Double,
    val type: String,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "parent_id")
    var parent: TransactionEntity? = null,

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var children: MutableList<TransactionEntity> = mutableListOf()

) {
    override fun toString(): String {
        return "TransactionEntity(id=$id, amount=$amount, type='$type', parent_id=${parent?.id})"
    }
}
