package com.example.o2zadanie.ui.activate

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
import com.example.o2zadanie.ui.common.O2Button

@Composable
fun ActivateTicketScreen(
    viewModel: ActivateTicketViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = screenState.statusText,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        O2Button(
            text = "Activate ticket",
            onClick = viewModel::onActivateTicket,
            enabled = screenState.isActivateButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}