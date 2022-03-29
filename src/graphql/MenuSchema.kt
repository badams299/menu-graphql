package com.example.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.services.MenuService
import com.apurebase.kgraphql.Context
import com.example.models.*


fun SchemaBuilder.menuSchema(menuService: MenuService) {

    query("getMenu") {
        description = "Get an existing menu"
        resolver { menuId: String ->
            try {
                menuService.getMenu(menuId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("getMenus") {
        description = "Get an existing menu"
        resolver { restaurantId: String ->
            try {
                menuService.getMenus(restaurantId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createMenu") {
        description = "Create a new menu"
        resolver { restaurantId: String, menuInput: MenuInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                menuService.createMenu(userId, restaurantId, menuInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateMenu") {
        description = "Update an existing review"
        resolver { menuId: String, menuInput: MenuInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                menuService.updateMenu(userId, menuId, menuInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteMenu") {
        description = "Delete a menu"
        resolver { menuId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                menuService.deleteMenu(userId, menuId)
            } catch (e: Exception) {
                null
            }
        }
    }

    inputType<MenuInput>{
        description = "The input of the review without the identifier"
    }

    type<Menu>{
        description = "Review object with the attributes text and rating"
    }
}