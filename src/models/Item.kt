package com.example.models

// 1
data class Item(
    override val id: String,
    val userId: String,
    val menuId : String,
    var name: String,
    var description: String,
    var imageUrl: String,
    var category: String,
    var dietaryPreference: String
) : Model

// the model that we are going to send when we want to modify the data source.
data class ItemInput(
    val name: String,
    val description: String,
    val imageUrl: String,
    var category: String,
    var dietaryPreference: String,
)

// 1
data class PagingInfo(var count: Int, var pages: Int, var next: Int?, var prev: Int?)

// 2
data class ItemsPage(val results: List<Item>, val info: PagingInfo)