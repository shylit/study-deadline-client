package ru.mirea.shylit.studydeadline.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getSearchHistory(): Flow<List<String>>

    suspend fun addQuery(query: String)

    suspend fun clearHistory()
}