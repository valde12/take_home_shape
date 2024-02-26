package com.example.dogapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.dogapp.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController) {

    Scaffold (
        bottomBar = { BottomNavBar(navController = navController) },
    ) {
            innerPadding -> Modifier.padding(innerPadding)
    }
}