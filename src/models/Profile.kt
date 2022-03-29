package com.example.models

data class Profile(val user: User, val restaurants: List<Restaurant> = emptyList())