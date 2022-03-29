package com.example.services

import com.example.models.Item
import com.example.models.ItemInput
import com.example.models.ItemsPage
import com.example.repository.ItemRepository
import com.mongodb.client.MongoClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class ItemService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: ItemRepository = ItemRepository(client)

    fun getItem(id: String): Item {
        return repo.getById(id)
    }

    fun createItem(itemInput: ItemInput, userId: String, menuId: String): Item {
        val uid = UUID.randomUUID().toString()
        val item = Item(
            id = uid,
            userId = userId,
            menuId = menuId,
            name = itemInput.name,
            description = itemInput.description,
            imageUrl = itemInput.imageUrl,
            dietaryPreference = itemInput.dietaryPreference,
            category = itemInput.category
        )
        return repo.add(item)
    }

    fun updateItem(userId: String, itemId: String, itemInput: ItemInput): Item {
        val item = repo.getById(itemId)
        if (item.userId == userId) {
            val updates = Item(
                id = itemId,
                userId = userId,
                menuId = item.menuId,
                name = itemInput.name,
                description = itemInput.description,
                imageUrl = itemInput.imageUrl,
                dietaryPreference = itemInput.dietaryPreference,
                category = itemInput.category
            )
            return repo.update(updates)
        }
        error("Cannot update dessert")
    }

    fun deleteItem(userId: String, itemId: String): Boolean {
        val item = repo.getById(itemId)
        if (item.userId == userId) {
            return repo.delete(itemId)
        }
        error("Cannot delete dessert")
    }

    fun getItemsPage(menuId: String, page: Int, size: Int): ItemsPage {
        return repo.getItemPage(menuId, page, size)
    }
}