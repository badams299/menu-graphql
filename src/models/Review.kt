package com.example.models

// 1
data class Review(override val id: String, val userId: String, val dessertId: String, val text: String, val rating: Int): Model
