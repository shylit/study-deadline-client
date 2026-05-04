package ru.mirea.shylit.studydeadline.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.mirea.shylit.studydeadline.data.local.entities.SubjectEntity

@Dao
interface SubjectDao {

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    fun observeSubjects(): Flow<List<SubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Query("DELETE FROM subjects WHERE id = :id")
    suspend fun deleteSubject(id: String)

    @Query("DELETE FROM subjects")
    suspend fun clearSubjects()
}