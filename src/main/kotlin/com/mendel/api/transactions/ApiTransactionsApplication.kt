package com.mendel.api.transactions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan("com.mendel")
@SpringBootApplication(scanBasePackages = ["com.mendel"])
class ApiNotificationsApplication

fun main(args: Array<String>) {
    runApplication<ApiNotificationsApplication>(*args)
}
