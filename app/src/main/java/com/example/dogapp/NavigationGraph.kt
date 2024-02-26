package com.example.dogapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dogapp.screens.DogBreedImagesScreen
import com.example.dogapp.screens.FavoriteScreen
import com.example.dogapp.screens.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("favorites") {
            FavoriteScreen(navController = navController)
        }
        composable("breedimg/{breed}/{subbreed}"){
            val breed = it.arguments?.getString("breed")
            val subbreed = it.arguments?.getString("subbreed")
            if (breed != null) {
                DogBreedImagesScreen(navController = navController, breed = breed, subbreed = subbreed)
            }
        }
        composable("breedimg/{breed}/"){
            val breed = it.arguments?.getString("breed")
            val subbreed = it.arguments?.getString("subbreed")
            if (breed != null) {
                DogBreedImagesScreen(navController = navController, breed = breed, subbreed = subbreed)
            }
        }
    }
}
