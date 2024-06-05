package com.mendel.api.transactions.integrations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mendel.api.transactions.TestConstants.Companion.TRANSACTION_ID
import com.mendel.api.transactions.TestConstants.Companion.TYPE
import com.mendel.api.transactions.aTransactionsRequest
import com.mendel.api.transactions.adapter.controller.TransactionsController
import com.mendel.api.transactions.adapter.controller.mapper.toPreTransactions
import com.mendel.api.transactions.application.port.`in`.TransactionsInPort
import io.kotest.core.spec.style.FeatureSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.text.SimpleDateFormat

@SpringBootTest
@ActiveProfiles("test")
class TransactionsIntegrationSpec : FeatureSpec({
    lateinit var portIn: TransactionsInPort
    lateinit var controller: TransactionsController
    lateinit var mockMvc: MockMvc
    lateinit var objectMapper: MappingJackson2HttpMessageConverter

    beforeEach {
        portIn = mockk(relaxed = true)
        controller = TransactionsController(
            portIn
        )
        objectMapper = Jackson2ObjectMapperBuilder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"))
            .build<ObjectMapper>()
            .let { MappingJackson2HttpMessageConverter(it) }
        mockMvc =
            MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(objectMapper)
                .build()
    }

    feature("transactions integration") {
        scenario("PUT transaction") {
            val request = aTransactionsRequest()
            val preTransaction = request.toPreTransactions(TRANSACTION_ID)
            val response = 100L

            every {
                portIn.save(preTransaction)
            } returns response

            mockMvc.perform(
                MockMvcRequestBuilders
                    .put("/transactions/10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        jacksonObjectMapper().registerModule(JavaTimeModule()).setPropertyNamingStrategy(
                            PropertyNamingStrategies.SNAKE_CASE
                        )
                            .writeValueAsString(aTransactionsRequest())
                    )
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
        }

        scenario("get sum") {

            every {
                portIn.sum(TRANSACTION_ID)
            } returns 100.0

            mockMvc.perform(
                MockMvcRequestBuilders
                    .get("/transactions/sum/$TRANSACTION_ID")
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(100.0))
        }

        scenario("get type") {
            val response = listOf(10L)

            every {
                portIn.find(TYPE)
            } returns response

            mockMvc.perform(
                MockMvcRequestBuilders
                    .get("/transactions/types/$TYPE")
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(10))
        }
    }
})
