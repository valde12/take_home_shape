package com.example.dogapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dogapp.BottomNavBar
import com.example.dogapp.dogBreed
import com.example.dogapp.dogResponseList
import com.example.dogapp.viewmodels.HomeViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewmodel = viewModel()) {

    val breeds = viewModel.breeds.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllBreeds()
    }

    Scaffold (
        bottomBar = { BottomNavBar(navController = navController) },
    ) { innerPadding ->
        DogBreedList(navController, breeds.value, Modifier.padding(innerPadding) )
        Modifier.padding(innerPadding)
    }
}

@Composable
fun DogBreedList(navController: NavController, breeds: dogResponseList, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(breeds.message.entries.toList()) { breed ->
            if (breed.value.isNotEmpty()) {
                breed.value.forEach { subBreed ->
                    DogBreedListItem(navController, dogBreed(breed.key, subBreed, ""))
                }
            } else {
                DogBreedListItem(navController, dogBreed(breed.key, "", ""))
            }
        }
    }
}

@Composable
fun DogBreedListItem(navController: NavController, name: dogBreed){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { navController.navigate("breedimg/${name.breed}/${name.subbreed}")},
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween ){
            Text(text = if(name.subbreed != "") {name.subbreed + " " + name.breed} else {name.breed},
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Icon(imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Arrow Right",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(end = 16.dp))
        }
}