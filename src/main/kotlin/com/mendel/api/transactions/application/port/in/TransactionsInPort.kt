package com.mendel.api.transactions.application.port.`in`

import com.mendel.api.transactions.domain.Transaction

interface TransactionsInPort {
    fun find(type: String): List<Long>
}
