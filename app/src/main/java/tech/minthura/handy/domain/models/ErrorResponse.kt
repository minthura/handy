package tech.minthura.handy.domain.models

data class ErrorResponse(
    val description: String,
    val statusCode: Int
)