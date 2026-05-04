package ru.mirea.shylit.studydeadline.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.mirea.shylit.studydeadline.data.local.entities.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history ORDER BY createdAt DESC")
    fun observeHistory(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuery(query: SearchHistoryEntity)

    @Query("""
        DELETE FROM search_history 
        WHERE query NOT IN (
            SELECT query FROM search_history 
            ORDER BY createdAt DESC 
            LIMIT 10
        )
    """)
    suspend fun trimHistory()

    @Query("DELETE FROM search_history")
    suspend fun clearHistory()
}