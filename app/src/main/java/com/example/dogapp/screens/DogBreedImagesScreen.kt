package com.example.dogapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.dogapp.BottomNavBar
import com.example.dogapp.dogBreed
import com.example.dogapp.viewmodels.DogBreedImagesViewmodel

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
        DogBreedImagesView(dogBreedImages.value)
        Modifier.padding(innerPadding)
    }
}

@Composable
fun DogBreedImagesView(dogBreedImages: List<dogBreed>) {
    LazyColumn(
        modifier = Modifier

    ) {
        items(dogBreedImages) { dogBreed ->
            DogBreedImageItem(dogBreed)
        }
    }
}

@Composable
fun DogBreedImageItem(dogBreed: dogBreed) {
    Box(
        Modifier.clickable { /*TODO*/ }
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
            .align(Alignment.BottomEnd)
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)) {
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