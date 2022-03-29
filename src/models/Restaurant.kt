package com.example.models

// 1
data class Restaurant(
    override val id: String,
    val userId: String,
    var name: String,
    var menus: List<Menu> = emptyList()
): Model

// the model that we are going to send when we want to modify the data source.
data class RestaurantInput(val name: String)