package dev.simonas.fadecandyrest

fun log(obj: Any) {
    System.out.println(obj.toString())
}

fun <T> tryToResult(exec: () -> T): Result<T> {
    return try {
        val value = exec()
        Result.success(value)
    } catch (e: Throwable) {
        e.printStackTrace()
        Result.failure(e)
    }
}