package com.mendel.api.transactions.adapter.db.model

import com.mendel.api.transactions.domain.Transaction
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity(name = "TRANSACTION")
data class TransactionEntity(
    @Id
    val id: Long,
    val amount: Double,
    val type: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: TransactionEntity? = null,

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var children: MutableList<TransactionEntity> = mutableListOf()

){
    override fun toString(): String {
        return "TransactionEntity(id=$id, amount=$amount, type='$type', parent_id=${parent?.id})"
    }
}