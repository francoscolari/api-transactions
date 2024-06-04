package com.mendel.api.transactions.adapter.db.mapper

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
