package com.example.o2zadanie.domain.model

sealed class TicketState(val code: String?) {
    object Unwiped: TicketState(null)
    class Wiped(code: String): TicketState(code)
    class Activated(code: String): TicketState(code)
    class Activating(code: String): TicketState(code)
    // TODO pridat moznost allowRetry pre ActivationError pre pripad chyb typu "No internet connection"
    class ActivationError(code: String, val error: String?): TicketState(code)
}
