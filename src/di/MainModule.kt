package com.example.di

import org.koin.dsl.module
import org.litote.kmongo.KMongo

//const val URI = "mongodb+srv://test:AxIADWGufa0Et4RB@cluster0.sq7r2.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"

val mainModule = module(createdAtStart = true) {
    factory { KMongo.createClient(System.getenv("MONGO_URI") ?: "") }
}