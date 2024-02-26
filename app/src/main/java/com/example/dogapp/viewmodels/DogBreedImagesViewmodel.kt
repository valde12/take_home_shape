package com.example.dogapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dogapp.api.APIHandler
import com.example.dogapp.dogBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class DogBreedImagesViewmodel : ViewModel() {
    private val apiHandler = APIHandler.getInstance()
    private val _dogBreedImages = MutableStateFlow<List<dogBreed>>(emptyList())
    val dogBreedImages = _dogBreedImages.asStateFlow()

    suspend fun getDogBreedImages(breed: String, subbreed: String?) {
        Log.e("DogBreedImagesViewmodel", "Breed: $breed, Subbreed: $subbreed")
        val dogBreedImgList = mutableListOf<dogBreed>()
        val dogImageList = if (subbreed != null) {
                apiHandler.getSubBreedImages(breed, subbreed)
            } else {
                apiHandler.getBreedImages(breed)
            }

        Log.e("DogBreedImagesViewmodel", "API response: $dogImageList")
        dogImageList?.message?.forEach {
            dogBreedImgList.add(dogBreed(breed, subbreed ?: "", it))
        }
        _dogBreedImages.value = dogBreedImgList
    }
}