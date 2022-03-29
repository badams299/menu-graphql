package com.example.services

import com.example.models.Menu
import com.example.models.MenuInput
import com.example.repository.MenuRepository
import com.mongodb.client.MongoClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class MenuService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo = MenuRepository(client)

    fun getMenus(restaurantId: String): List<Menu> {
        return repo.getMenuByRestaurantId(restaurantId)
    }

    fun getMenu(id: String): Menu {
        return repo.getById(id)
    }

    fun createMenu(userId: String, restaurantId: String, menuInput: MenuInput): Menu {
        val uid = UUID.randomUUID().toString()
        val menu = Menu(
            id = uid,
            userId = userId,
            restaurantId = restaurantId,
            name = menuInput.name
        )
        return repo.add(menu)
    }

    fun updateMenu(userId: String, menuId: String, menuInput: MenuInput): Menu {
        val menu = repo.getById(menuId)
        if (menu.userId == userId) {
            val updates = Menu(
                id = menuId,
                restaurantId = menu.restaurantId,
                userId = userId,
                name = menuInput.name
            )
            return repo.update(updates)
        }
        error("Cannot update review")
    }

    fun deleteMenu(userId: String, menuId: String): Boolean {
        val menu = repo.getById(menuId)
        if (menu.userId == userId) {
            return repo.delete(menuId)
        }
        error("Cannot delete review")
    }
}