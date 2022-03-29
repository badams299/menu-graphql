package com.example

import com.apurebase.kgraphql.GraphQL
import com.example.di.mainModule
import com.example.graphql.*
import com.example.services.*
import io.ktor.application.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    log.info("Hello from module!")

    startKoin {
        modules(mainModule)
    }

    install(GraphQL) {
        val itemService = ItemService()
        val menuService = MenuService()
        val restaurantService = RestaurantService()
        val authService = AuthService()
        val profileService = ProfileService()

        playground = true

        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }

        schema {
            authSchema(authService)
            itemSchema(itemService)
            menuSchema(menuService)
            restaurantSchema(restaurantService)
            profileSchema(profileService)
        }
    }
}