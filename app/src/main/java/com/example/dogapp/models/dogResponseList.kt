package com.example.dogapp.models

data class dogResponseList(
    val message: Map<String, List<String>>,
    val status: String
)
