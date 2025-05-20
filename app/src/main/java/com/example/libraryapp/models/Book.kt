package com.example.libraryapp.models

data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val year: Int,
    val description: String
)