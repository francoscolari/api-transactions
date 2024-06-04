package com.mendel.api.transactions.application.port.`in`

import com.mendel.api.transactions.domain.PreTransaction

interface TransactionsInPort {
    fun save(preTransaction: PreTransaction): Long
    fun find(type: String): List<Long>
    fun sum(id: Long): Double
}
