package com.mendel.api.transactions.adapter.controller

import com.mendel.api.transactions.application.port.`in`.TransactionsInPort
import com.mendel.api.transactions.shared.log.CompanionLogger
import com.mendel.api.transactions.shared.log.benchmark
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionsController(
    private val transactionsInPort: TransactionsInPort
) {

    @GetMapping("/types/{type}")
    fun getTransactions(
        @PathVariable type: String,
    ) = log.benchmark("transactions: get") {
        transactionsInPort.find(type).log { info("get transactions response: {}", it) }
    }

    companion object : CompanionLogger()
}