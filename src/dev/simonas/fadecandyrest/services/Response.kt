package dev.simonas.fadecandyrest.services

class Response<T>(
    val value: T?,
    val isSuccess: Boolean,
    val errorMessage: String?,
    val stackTrace: String?
) {

    constructor(result: Result<T>): this(
        value = result.getOrNull(),
        isSuccess = result.isSuccess,
        errorMessage = result.exceptionOrNull()?.message,
        stackTrace = result.exceptionOrNull()?.stackTrace?.joinToString { "$it\n" }
    )
}