package com.mendel.api.transactions.shared.error.provider

import com.mendel.api.transactions.shared.error.model.ApiError
import com.mendel.api.transactions.shared.error.model.ApiErrorResponse
import com.mendel.api.transactions.shared.error.model.ApplicationError
import com.mendel.api.transactions.shared.log.CompanionLogger
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class ErrorResponseProvider {

    fun provideFor(error: ApplicationError) =
        ApiErrorResponse(
            datetime = OffsetDateTime.now(),
            errors = listOf(
                ApiError(
                    message = error.message,
                )
            )
        ).log { debug("error response provided {}", it) }

    companion object : CompanionLogger()
}
