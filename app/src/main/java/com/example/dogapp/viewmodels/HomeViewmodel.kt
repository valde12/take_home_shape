package com.example.dogapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.api.APIHandler
import com.example.dogapp.dogResponseList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewmodel : ViewModel() {

    private val apiHandler = APIHandler.getInstance()

    private val _breeds = MutableStateFlow(dogResponseList(emptyMap(), ""))
    val breeds = _breeds.asStateFlow()
    suspend fun getAllBreeds() {
        viewModelScope.launch {
            _breeds.value = apiHandler.getAllBreeds()!!
        }
    }
}