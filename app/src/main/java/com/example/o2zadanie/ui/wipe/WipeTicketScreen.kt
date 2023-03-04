package com.example.o2zadanie.ui.wipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.o2zadanie.ui.common.O2Button


@Composable
fun WipeTicketScreen(
    viewModel: WipeTicketViewModel = hiltViewModel()
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
            screenState.code?.let { code ->
                Text(
                    text = code,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            this@Column.AnimatedVisibility(
                visible = screenState.isUnwiped,
                enter = EnterTransition.None,
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 800)
                ),
            ) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(Color(0xff9e9e9e))
                        .padding(horizontal = 16.dp)
                )
            }
            if(screenState.isWiping) {
                Text(
                    text = "Wiping...",
                    style = MaterialTheme.typography.h5
                )
            }
        }
        O2Button(
            text = "Wipe ticket",
            onClick = viewModel::onWipeTicket,
            enabled = screenState.isWipeButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}