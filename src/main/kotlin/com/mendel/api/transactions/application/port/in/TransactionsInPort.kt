package com.mendel.api.transactions.application.port.`in`


interface TransactionsInPort {
    fun find(type: String): List<Long>
    fun sum(id: Long): Double
}
