package com.example.o2zadanie.core.navigation

import androidx.navigation.NavHostController

class O2NavActions(
    private val navController: NavHostController
) {
    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToWipe() {
        navController.navigate(route = "wipe")
    }

    fun navigateToActivate() {
        navController.navigate(route = "activate")
    }
}