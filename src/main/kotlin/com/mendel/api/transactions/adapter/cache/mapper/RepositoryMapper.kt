package com.mendel.api.transactions.adapter.cache.mapper

import com.mendel.api.transactions.adapter.cache.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction

fun TransactionEntity.toTransaction(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        type = type,
        parentId = parentId,
    )

fun Transaction.toTransactionEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        amount = amount,
        type = type,
        parentId = parentId,
    )
