package ru.mirea.shylit.studydeadline.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun observeDarkTheme(): Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)
}