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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dogapp.BottomNavBar
import com.example.dogapp.models.dogBreed
import com.example.dogapp.viewmodels.FavoriteViewmodel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewmodel = viewModel()) {
    val favoriteImages = viewModel.dogBreedFavoriteImages.collectAsState()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedBreed by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.retrieveFavorites(context)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        val breeds = favoriteImages.value.map { it.breed }.distinct()
                        breeds.forEach { breed ->
                            DropdownMenuItem(text = { Text(text = breed) }, onClick = {
                                selectedBreed = breed
                                expanded = false } )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) },
    ) { innerPadding ->
        FavoriteImagesView(viewModel, favoriteImages.value, selectedBreed, Modifier.padding(innerPadding))
    }
}

@Composable
fun FavoriteImagesView(viewmodel: FavoriteViewmodel, dogBreedImages: List<dogBreed>, selectedBreed: String, modifier: Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("dog_app", Context.MODE_PRIVATE)
    val gson = Gson()

    // Retrieve the existing list from shared preferences
    val dogBreedListJson = sharedPreferences.getString("favorites", "")
    val type = object : TypeToken<List<dogBreed>>() {}.type
    val dogBreedList = gson.fromJson(dogBreedListJson, type) as? MutableList<dogBreed> ?: mutableListOf()

    // Filter the list based on the selected breed
    val filteredDogBreedImages = if (selectedBreed.isNotEmpty()) dogBreedImages.filter { it.breed == selectedBreed } else dogBreedImages

    LazyColumn(
        modifier = modifier
    ) {
        items(filteredDogBreedImages) { dogBreed ->
            FavoriteImageItem(dogBreed, viewmodel) { clickedDogBreed ->
                // Remove the clicked dogBreed from the list
                dogBreedList.remove(clickedDogBreed)
                val updatedDogBreedListJson = gson.toJson(dogBreedList)
                sharedPreferences.edit().putString("favorites", updatedDogBreedListJson).apply()
            }
        }
    }
}
@Composable
fun FavoriteImageItem(dogBreed: dogBreed, viewmodel: FavoriteViewmodel, onImageClick: (dogBreed: dogBreed) -> Unit) {
    val context = LocalContext.current
    Box(
    ){
        AsyncImage(
            model = dogBreed.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Box(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.BottomStart)
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)){
            Text(
                text = if(dogBreed.subbreed != "") {dogBreed.subbreed + " " + dogBreed.breed} else {dogBreed.breed},
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center).padding(8.dp)
            )
        }
        Box(modifier = Modifier
            .padding(16.dp)
            .align(Alignment.BottomEnd)
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .clickable {
                onImageClick(dogBreed)
                viewmodel.retrieveFavorites(context)}){
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center))
        }
    }
}