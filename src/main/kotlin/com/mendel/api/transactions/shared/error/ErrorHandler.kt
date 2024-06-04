package com.mendel.api.transactions.shared.error

import com.mendel.api.transactions.shared.error.model.ApiErrorResponse
import com.mendel.api.transactions.shared.error.model.ApplicationError
import com.mendel.api.transactions.shared.error.model.ApplicationError.Companion.notFound
import com.mendel.api.transactions.shared.error.provider.ErrorResponseProvider
import com.mendel.api.transactions.shared.log.CompanionLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler(
    private val errorResponseProvider: ErrorResponseProvider
) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundException(th: NoSuchElementException): ResponseEntity<ApiErrorResponse> =
        notFound(
            errorMessage = "Not found",
            throwable = th
        ).asResponse()

    private fun ApplicationError.asResponse(): ResponseEntity<ApiErrorResponse> =
        errorResponseProvider.provideFor(this)
            .pairedWith(status)
            .asResponseEntity()

    fun <T, S> T.pairedWith(second: S) =
        this to second

    fun <T> Pair<T, HttpStatus>.asResponseEntity() =
        ResponseEntity(
            first, second
        )
    companion object : CompanionLogger()
}
