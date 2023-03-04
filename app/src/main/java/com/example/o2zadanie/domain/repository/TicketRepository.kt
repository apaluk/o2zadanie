package com.example.o2zadanie.domain.repository

import com.example.o2zadanie.domain.model.TicketState
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun getTicket(): Flow<TicketState>
    fun wipeTicket(code: String)
    suspend fun activateTicket(code: String)
}