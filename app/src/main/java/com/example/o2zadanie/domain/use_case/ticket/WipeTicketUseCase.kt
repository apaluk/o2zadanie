package com.example.o2zadanie.domain.use_case.ticket

import com.example.o2zadanie.domain.repository.TicketRepository
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class WipeTicketUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) {

    suspend operator fun invoke() {
        delay(2000)
        val code = UUID.randomUUID().toString()
        ticketRepository.wipeTicket(code )
    }

}