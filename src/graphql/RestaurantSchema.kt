package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.*
import com.example.services.MenuService
import com.example.services.RestaurantService

fun SchemaBuilder.restaurantSchema(restaurantService: RestaurantService) {

    query("getRestaurant") {
        description = "Get an existing restaurant"
        resolver { restaurantId: String ->
            try {
                restaurantService.getRestaurant(restaurantId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createRestaurant") {
        description = "Create a new restaurant"
        resolver { restaurantInput: RestaurantInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                restaurantService.createRestaurant(userId, restaurantInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateRestaurant") {
        description = "Update an existing restaurant"
        resolver { restaurantId: String, restaurantInput: RestaurantInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                restaurantService.updateRestaurant(userId, restaurantId, restaurantInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteRestaurant") {
        description = "Delete a restaurant"
        resolver { restaurantId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                restaurantService.deleteRestaurant(userId, restaurantId)
            } catch (e: Exception) {
                null
            }
        }
    }

    inputType<RestaurantInput> {
        description = "The input of the review without the identifier"
    }

    type<Restaurant> {
        description = "Review object with the attributes text and rating"
    }
}