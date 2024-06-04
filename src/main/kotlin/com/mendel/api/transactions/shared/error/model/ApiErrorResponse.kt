package com.mendel.api.transactions.shared.error.model

import java.time.OffsetDateTime

data class ApiErrorResponse(
    val datetime: OffsetDateTime,
    val errors: List<ApiError>
)

data class ApiError(
    val message: String,
)
