package com.mendel.api.transactions.adapter.db.mapper

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction

fun TransactionEntity.toTransaction(visitedEntities: MutableSet<Long> = mutableSetOf()): Transaction {
    if (visitedEntities.contains(id)) {
        return Transaction(id = id, amount = amount, type = type)
    }
    visitedEntities.add(id)
    val parentTransaction = parent?.toTransaction(visitedEntities)
    val childTransactions = children.map { it.toTransaction(visitedEntities) }.toMutableList()
    visitedEntities.remove(id)

    return Transaction(
        id = id,
        amount = amount,
        type = type,
        parent = parentTransaction,
        children = childTransactions
    )
}

fun Transaction.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        parent = parent?.let {
            it.toTransactionEntity()
        }
    )
