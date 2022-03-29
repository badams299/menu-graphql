package com.example.models

// 1
data class Menu(
    override val id: String,
    val userId: String,
    val restaurantId: String,
    var name: String,
    var items: List<Item> = emptyList()
) : Model

// the model that we are going to send when we want to modify the data source.
data class MenuInput(val name: String)