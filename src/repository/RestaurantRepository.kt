package com.example.repository

import com.example.models.Restaurant
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class RestaurantRepository(client: MongoClient) : RepositoryInterface<Restaurant> {
    override lateinit var col: MongoCollection<Restaurant>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Restaurant>("Restaurant")
    }

    fun getRestaurantById(userId: String): List<Restaurant> {
        return try {
            col.find(Restaurant::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get user desserts")
        }
    }
}