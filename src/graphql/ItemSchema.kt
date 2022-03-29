package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.Item
import com.example.models.ItemInput
import com.example.models.User
import com.example.services.ItemService

fun SchemaBuilder.itemSchema(itemService: ItemService) {

    // 1
    inputType<ItemInput> {
        description = "The input of the item without the identifier"
    }

    type<Item> {
        description = "Item object with attributes name, description and imageUrl"
    }

    query("item") {
        resolver { itemId: String ->
            try {
                itemService.getItem(itemId)
            } catch (e: Exception) {
                null
            }
        }
    }

    // 2
    query("items") {
        resolver { menuId: String, page: Int?, size: Int? ->
            try {
                itemService.getItemsPage(menuId, page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createItem") {
        description = "Create a new item"
        resolver { itemInput: ItemInput, menuId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                itemService.createItem(itemInput, userId, menuId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateItem") {
        description = "Updates a item"
        resolver { itemId: String, itemInput: ItemInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                itemService.updateItem(userId, itemId, itemInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteItem") {
        description = "Deletes a item"
        resolver { dessertId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                itemService.deleteItem(userId, dessertId)
            } catch (e: Exception) {
                false
            }
        }
    }
}
