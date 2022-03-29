package com.example.repository

import com.example.models.Item
import com.example.models.ItemsPage
import com.example.models.PagingInfo
import com.example.models.Review
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ItemRepository(client: MongoClient) : RepositoryInterface<Item> {
    override lateinit var col: MongoCollection<Item>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Item>("Item")
    }

    fun getItemByUserId(userId: String): List<Item> {
        return try {
            col.find(Item::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get user desserts")
        }
    }

    fun getItemsByMenuId(menuId: String): List<Item> {
        return try {
            val res = col.find(Item::menuId eq menuId)
                ?: throw Exception("No review with that dessert ID exists")
            res.asIterable().map { it }
        } catch(t: Throwable) {
            throw Exception("Cannot find reviews")
        }
    }

    fun getItemPage(menuId: String, page: Int, size: Int): ItemsPage {
        try {
            val skips = page * size
            val res = col.find(Item::menuId eq menuId).skip(skips).limit(size)
                ?: throw Exception("No desserts exist")
            val results = res.asIterable().map { it }
            val totalDesserts = col.estimatedDocumentCount()
            val totalPages = (totalDesserts / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalDesserts.toInt(), totalPages.toInt(), next, prev)
            return ItemsPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get desserts page")
        }
    }
}
