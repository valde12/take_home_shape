package com.example.dogapp.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dogapp.BottomNavBar
import com.example.dogapp.models.dogBreed
import com.example.dogapp.viewmodels.DogBreedImagesViewmodel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedImagesScreen(navController: NavController, breed: String, subbreed: String?, viewModel: DogBreedImagesViewmodel = viewModel()) {
    val dogBreedImages = viewModel.dogBreedImages.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getDogBreedImages(breed, subbreed)
    }

    Scaffold (
        bottomBar = { BottomNavBar(navController = navController) },
    ) { innerPadding ->
        DogBreedImagesView(dogBreedImages.value, Modifier.padding(innerPadding))
        Modifier.padding(innerPadding)
    }
}

@Composable
fun DogBreedImagesView(dogBreedImages: List<dogBreed>, modifier: Modifier) {
    // Get the current context
    val context = LocalContext.current
    // Get the shared preferences
    val sharedPreferences = context.getSharedPreferences("dog_app", Context.MODE_PRIVATE)
    // Create a Gson object to convert JSON to data classes and vice versa
    val gson = Gson()

    // Retrieve the list of favorite dog breeds from SharedPreferences
    val dogBreedListJson = sharedPreferences.getString("favorites", "")
    val type = object : TypeToken<List<dogBreed>>() {}.type
    val dogBreedList = gson.fromJson(dogBreedListJson, type) as? MutableList<dogBreed> ?: mutableListOf()

    LazyColumn(
        modifier = modifier
    ) {
        items(dogBreedImages) { dogBreed ->
            DogBreedImageItem(dogBreed, dogBreedList) { clickedDogBreed ->
                // Add the clicked dog breed to the favorites list
                dogBreedList.add(clickedDogBreed)
                // Update the favorites list in SharedPreferences
                val updatedDogBreedListJson = gson.toJson(dogBreedList)
                sharedPreferences.edit().putString("favorites", updatedDogBreedListJson).apply()
            }
        }
    }
}

@Composable
fun DogBreedImageItem(dogBreed: dogBreed, favoriteList: MutableList<dogBreed>, onImageClick: (dogBreed: dogBreed) -> Unit) {
    var iconFill by remember { mutableStateOf(favoriteList.contains(dogBreed)) }

    // Display the dog breed image
    Box(){
        AsyncImage(
            model = dogBreed.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        // Display a clickable icon on the image
        Box(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.BottomEnd)
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .clickable { onImageClick(dogBreed)
                        iconFill = !iconFill}){
            Icon(
                imageVector = if(iconFill) { Icons.Filled.Favorite } else { Icons.Filled.FavoriteBorder },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center))
        }

    }
}