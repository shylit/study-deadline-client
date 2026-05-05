package ru.mirea.shylit.studydeadline.domain.repositories

interface AuthRepository {

    fun isUserAuthorized(): Boolean

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun register(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun getIdToken(): Result<String>

    fun logout()
}