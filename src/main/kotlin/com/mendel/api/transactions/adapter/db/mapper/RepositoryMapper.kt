package com.mendel.api.transactions.adapter.db.mapper

import com.mendel.api.transactions.adapter.db.model.TransactionEntity
import com.mendel.api.transactions.domain.Transaction

fun TransactionEntity.toTransaction(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        type = type,
        parentId = parentId,
    )