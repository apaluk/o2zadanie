package com.example.o2zadanie.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o2zadanie.domain.model.TicketState
import com.example.o2zadanie.domain.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TicketOverviewViewModel @Inject constructor(
    ticketRepository: TicketRepository
): ViewModel() {
    val screenState: StateFlow<TicketOverviewScreenState> =
        ticketRepository.getTicket()
            .map { it.toTicketOverviewScreenState() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TicketOverviewScreenState())
}

data class TicketOverviewScreenState(
    val ticketState: TicketState = TicketState.Unwiped,
    val isWipeButtonEnabled: Boolean = false,
    val isActivateButtonEnabled: Boolean = false
)

fun TicketState.toTicketOverviewScreenState(): TicketOverviewScreenState =
    TicketOverviewScreenState(
        ticketState = this,
        isWipeButtonEnabled = this == TicketState.Unwiped,
        isActivateButtonEnabled = this is TicketState.Wiped
    )