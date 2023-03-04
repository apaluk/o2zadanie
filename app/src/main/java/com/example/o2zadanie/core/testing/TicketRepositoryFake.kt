package com.example.o2zadanie.core.testing

import com.example.o2zadanie.domain.model.TicketState
import com.example.o2zadanie.domain.repository.TicketRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TicketRepositoryFake: TicketRepository {

    val ticket = MutableStateFlow<TicketState>(TicketState.Unwiped)

    override fun getTicket(): Flow<TicketState> = ticket

    override fun wipeTicket(code: String) {
        if(ticket.value == TicketState.Unwiped) {
            ticket.value = TicketState.Wiped(code)
        }
    }

    override suspend fun activateTicket(code: String) {
        ticket.value = TicketState.Activating(code)
        delay(3000)
        ticket.value = if(code == "invalid")
            TicketState.ActivationError(code, "invalid code")
        else
            TicketState.Activated(code)
    }
}