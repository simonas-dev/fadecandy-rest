package dev.simonas.fadecandyrest.fadecandy

sealed class FadecandyError : Throwable() {
    class AlreadyRunning: FadecandyError()
}