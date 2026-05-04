package ru.mirea.shylit.studydeadline.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mirea.shylit.studydeadline.data.local.dao.SearchHistoryDao
import ru.mirea.shylit.studydeadline.data.local.entities.SearchHistoryEntity
import ru.mirea.shylit.studydeadline.domain.repositories.SearchHistoryRepository
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {

    override fun getSearchHistory(): Flow<List<String>> {
        return searchHistoryDao.observeHistory()
            .map { history -> history.map { it.query } }
    }

    override suspend fun addQuery(query: String) {
        val normalizedQuery = query.trim()

        if (normalizedQuery.isBlank()) return

        searchHistoryDao.insertQuery(
            SearchHistoryEntity(
                query = normalizedQuery,
                createdAt = System.currentTimeMillis()
            )
        )

        searchHistoryDao.trimHistory()
    }

    override suspend fun clearHistory() {
        searchHistoryDao.clearHistory()
    }
}