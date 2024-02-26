package com.example.dogapp.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.dogapp.dogBreed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteViewmodel : ViewModel() {
    private val _dogBreedFavoriteImages = MutableStateFlow<List<dogBreed>>(emptyList())
    val dogBreedFavoriteImages = _dogBreedFavoriteImages.asStateFlow()

    fun retrieveFavorites(context: Context) {
        val sharedPreferences = context.getSharedPreferences("dog_app", Context.MODE_PRIVATE)
        val gson = Gson()
        val dogBreedListJson = sharedPreferences.getString("favorites", "")
        val type = object : TypeToken<List<dogBreed>>() {}.type
        val dogBreedList = gson.fromJson(dogBreedListJson, type) as? MutableList<dogBreed> ?: mutableListOf()
        _dogBreedFavoriteImages.value = dogBreedList
    }
}