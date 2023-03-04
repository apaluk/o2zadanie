package com.example.o2zadanie.ui.overview

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.o2zadanie.core.navigation.O2NavActions
import com.example.o2zadanie.domain.model.TicketState
import com.example.o2zadanie.ui.common.O2Button

@Composable
fun TicketOverviewScreen(
    navActions: O2NavActions,
    viewModel: TicketOverviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.screenState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = uiState.ticketState.text(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            O2Button(
                text = "Wipe",
                onClick = navActions::navigateToWipe,
                enabled = uiState.isWipeButtonEnabled,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
            O2Button(
                text = "Activate",
                onClick = navActions::navigateToActivate,
                enabled = uiState.isActivateButtonEnabled,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        }

    }
}

@Composable
fun TicketState.text(): String =
    when(this) {
        is TicketState.Activated -> "Your ticket was successfully activated"
        is TicketState.ActivationError -> "Your ticket was not successfully activated (Error: $error)"
        is TicketState.Activating -> "Activating ticket"
        TicketState.Unwiped -> "Your ticket is unwiped"
        is TicketState.Wiped -> "Your ticket is ready for activation"
    }