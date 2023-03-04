package com.example.o2zadanie.ui.wipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o2zadanie.domain.model.TicketState
import com.example.o2zadanie.domain.repository.TicketRepository
import com.example.o2zadanie.domain.use_case.ticket.WipeTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WipeTicketViewModel @Inject constructor(
    private val wipeTicketUseCase: WipeTicketUseCase,
    ticketRepository: TicketRepository
): ViewModel() {

    private val isWipingTicket = MutableStateFlow(false)

    val screenState =
        combine(ticketRepository.getTicket(), isWipingTicket) { ticket, isWiping ->
            WipeTicketScreenState(
                isWipeButtonEnabled = ticket is TicketState.Unwiped && !isWiping,
                isUnwiped = ticket is TicketState.Unwiped,
                isWiping = isWiping,
                code = ticket.code
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WipeTicketScreenState())

    fun onWipeTicket() {
        viewModelScope.launch {
            isWipingTicket.value = true
            wipeTicketUseCase()
            isWipingTicket.value = false
        }
    }
}

data class WipeTicketScreenState(
    val isWipeButtonEnabled: Boolean = false,
    val isUnwiped: Boolean = true,
    val isWiping: Boolean = false,
    val code: String? = null
)
