package com.example.models

// 1
data class User(override val id: String, val email: String, val hashedPass: ByteArray): Model

// 2
data class UserInput(val email: String, val password: String)

// 3
data class UserResponse(val token: String, val user: User)