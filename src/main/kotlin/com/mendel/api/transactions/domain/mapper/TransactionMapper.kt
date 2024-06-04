package com.mendel.api.transactions.domain.mapper

import com.mendel.api.transactions.domain.PreTransaction
import com.mendel.api.transactions.domain.Transaction

fun PreTransaction.toTransaction(parentTransaction: Transaction?): Transaction =
    Transaction(
        id = id,
        type = type,
        amount = amount,
        parent = parentTransaction?.let {
            Transaction(
                id = it.id, type = it.type,
                amount = it.amount
            )
        }
    )