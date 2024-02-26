package com.example.dogapp

data class dogResponseList(
    val message: Map<String, List<String>>,
    val status: String
)
