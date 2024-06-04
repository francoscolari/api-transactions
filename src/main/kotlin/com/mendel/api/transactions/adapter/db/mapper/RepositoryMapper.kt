package com.mendel.api.transactions.adapter.db.mapper

import com.mendel.api.transactions.adapter.db.TransactionsRepository.Companion.log
import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction

fun TransactionEntity.toTransaction(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        type = type,
        parent = parent?.toTransaction(),
        children = children.map { transactionEntity ->
            Transaction(
                id = transactionEntity.id,
                amount = transactionEntity.amount,
                type = transactionEntity.type,
                parent = transactionEntity.parent?.let { parentEntity ->
                    Transaction(
                        id = parentEntity.id,
                        amount = parentEntity.amount,
                        type = parentEntity.type
                    )
                },
                children = transactionEntity.children.map { childEntity ->
                    Transaction(
                        id = childEntity.id,
                        amount = childEntity.amount,
                        type = childEntity.type
                    )
                }.toMutableList()
            )
        },
    )

fun Transaction.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        parent = parent?.let {
            TransactionEntity(
                id = it.id,
                type = it.type,
                amount = it.amount
            )
        }).log { info("model txs save: {}", it) }