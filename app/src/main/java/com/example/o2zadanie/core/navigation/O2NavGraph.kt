package com.example.o2zadanie.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.o2zadanie.ui.activate.ActivateTicketScreen
import com.example.o2zadanie.ui.overview.TicketOverviewScreen
import com.example.o2zadanie.ui.wipe.WipeTicketScreen

@Composable
fun O2NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navActions: O2NavActions = remember { O2NavActions(navController) }
) {
    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {
        composable(route = "main") {
            TicketOverviewScreen(navActions)
        }
        composable(route = "wipe") {
            WipeTicketScreen()
        }
        composable(route = "activate") {
            ActivateTicketScreen()
        }
    }
}