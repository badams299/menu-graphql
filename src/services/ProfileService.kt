package com.example.services

import com.example.models.Profile
import com.example.models.User
import com.example.repository.MenuRepository
import com.example.repository.RestaurantRepository
import com.example.repository.UserRepository
import com.mongodb.client.MongoClient
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileService: KoinComponent {
    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val restaurantRepo: RestaurantRepository = RestaurantRepository(client)

    fun getProfile(userId: String): Profile {
        val user = repo.getById(userId)
        val restaurants = restaurantRepo.getRestaurantById(userId)
        return Profile(user, restaurants)
    }

    fun getAllProfiles():List<User>{
        return repo.getAll()
    }
}