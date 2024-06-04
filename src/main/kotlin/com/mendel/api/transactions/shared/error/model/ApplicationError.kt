package com.mendel.api.transactions.shared.error.model

import org.springframework.http.HttpStatus

data class ApplicationError(
    val status: HttpStatus,
    val message: String,
    val origin: Throwable? = null
) {
    companion object {
        fun notFound(errorMessage: String? = "Not Found", throwable: Throwable? = null) =
            ApplicationError(
                message = errorMessage ?: "Not found",
                status = HttpStatus.NOT_FOUND,
                origin = throwable
            )
    }
}
