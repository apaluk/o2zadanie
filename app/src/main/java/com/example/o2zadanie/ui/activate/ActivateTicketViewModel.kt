package com.example.o2zadanie.ui.activate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o2zadanie.domain.model.TicketState
import com.example.o2zadanie.domain.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateTicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
): ViewModel() {

    val screenState = ticketRepository.getTicket().map {
        ActivateTicketScreenState(
            isActivateButtonEnabled = it is TicketState.Wiped,
            statusText = when(it) {
                is TicketState.Activating -> "Activating..."
                is TicketState.ActivationError -> "Activation error: ${it.error ?: "unknown" }"
                is TicketState.Activated -> "Activated"
                else -> ""
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ActivateTicketScreenState())

    fun onActivateTicket() {
        viewModelScope.launch {
            val ticket = ticketRepository.getTicket().first()
            val code = if(ticket is TicketState.Wiped) ticket.code else return@launch
            code ?: return@launch
            ticketRepository.activateTicket(code)
        }
    }
}

data class ActivateTicketScreenState(
    val isActivateButtonEnabled: Boolean = false,
    val statusText: String = ""
)