@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.o2zadanie.ui.overview

import com.example.o2zadanie.core.testing.TicketRepositoryFake
import com.example.o2zadanie.core.util.MainDispatcherRule
import com.example.o2zadanie.domain.model.TicketState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


// Nieco podobne by som spravil aj pre WipeTicketViewModel a ActivateTicketViewModel.
// Takisto by som este pridal nejake UI testy.
// Priklad: https://github.com/apaluk/ws-player-android/blob/main/app/src/androidTest/java/com/apaluk/wsplayer/ui/LoginScreenTest.kt
class TicketOverviewViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var ticketRepository: TicketRepositoryFake
    private lateinit var viewModel: TicketOverviewViewModel

    @Before
    fun setup() {
        ticketRepository = TicketRepositoryFake()
        viewModel = TicketOverviewViewModel(ticketRepository)
    }

    @Test
    fun `unwiped ticket, screen state is correct`() = runTest {
        ticketRepository.ticket.emit(TicketState.Unwiped)
        val screenState = viewModel.screenState.first()
        assertThat(screenState.isWipeButtonEnabled).isTrue()
        assertThat(screenState.isActivateButtonEnabled).isFalse()
    }

    @Test
    fun `wiped ticket, screen state is correct`() = runTest {
        ticketRepository.ticket.value = TicketState.Wiped("")
        val screenState = viewModel.screenState.first()
        assertThat(screenState.isWipeButtonEnabled).isFalse()
        assertThat(screenState.isActivateButtonEnabled).isTrue()
    }

    @Test
    fun `activating ticket, screen state is correct`() = runTest {
        ticketRepository.ticket.value = TicketState.Activating("")
        val screenState = viewModel.screenState.first()
        assertThat(screenState.isWipeButtonEnabled).isFalse()
        assertThat(screenState.isActivateButtonEnabled).isFalse()
    }

    @Test
    fun `activated ticket, screen state is correct`() = runTest {
        ticketRepository.ticket.value = TicketState.Activated("")
        val screenState = viewModel.screenState.first()
        assertThat(screenState.isWipeButtonEnabled).isFalse()
        assertThat(screenState.isActivateButtonEnabled).isFalse()
    }

    @Test
    fun `activation error ticket, screen state is correct`() = runTest {
        ticketRepository.ticket.value = TicketState.ActivationError("", "")
        val screenState = viewModel.screenState.first()
        assertThat(screenState.isWipeButtonEnabled).isFalse()
        assertThat(screenState.isActivateButtonEnabled).isFalse()
    }
}