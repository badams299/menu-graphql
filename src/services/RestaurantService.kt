package com.example.services

import com.example.models.Restaurant
import com.example.models.RestaurantInput
import com.example.repository.RestaurantRepository
import com.mongodb.client.MongoClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class RestaurantService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo = RestaurantRepository(client)

    fun getRestaurant(id: String): Restaurant {
        return repo.getById(id)
    }

    fun createRestaurant(userId: String, restaurantInput: RestaurantInput): Restaurant {
        val uid = UUID.randomUUID().toString()
        val restaurant = Restaurant(
            id = uid,
            userId = userId,
            name = restaurantInput.name
        )
        return repo.add(restaurant)
    }

    fun updateRestaurant(userId: String, restaurantId : String, restaurantInput: RestaurantInput): Restaurant {
        val restaurant = repo.getById(restaurantId)
        if (restaurant.userId == userId) {
            val updates = Restaurant(
                id = restaurantId,
                userId = userId,
                name = restaurantInput.name
            )
            return repo.update(updates)
        }
        error("Cannot update review")
    }

    fun deleteRestaurant(userId: String, restaurantId: String): Boolean {
        val restaurant = repo.getById(restaurantId)
        if (restaurant.userId == userId) {
            return repo.delete(restaurantId)
        }
        error("Cannot delete review")
    }
}