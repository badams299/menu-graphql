package com.example.repository

import com.example.models.Menu
import com.example.models.Review
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class MenuRepository(client: MongoClient) : RepositoryInterface<Menu> {
    override lateinit var col: MongoCollection<Menu>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Menu>("Menu")
    }

    fun getMenuByRestaurantId(restaurantId: String): List<Menu> {
        return try {
            val res = col.find(Menu::restaurantId eq restaurantId)
                ?: throw Exception("No review with that dessert ID exists")
            res.asIterable().map { it }
        } catch(t: Throwable) {
            throw Exception("Cannot find reviews")
        }
    }
}