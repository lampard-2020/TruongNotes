package com.notes.miniapp.repository

interface AuthenticationRepository {
    fun currentUserId(): String?
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signIn(email: String, password: String): Boolean
}
