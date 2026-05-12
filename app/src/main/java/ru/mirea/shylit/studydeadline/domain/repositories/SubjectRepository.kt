package ru.mirea.shylit.studydeadline.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.mirea.shylit.studydeadline.domain.models.Subject

interface SubjectRepository {

    fun getSubjects(): Flow<List<Subject>>

    suspend fun refreshSubjects()

    suspend fun createSubject(name: String, color: String? = null): Result<Subject>

    suspend fun updateSubject(
        id: String,
        name: String,
        description: String
    ): Result<Subject>

    suspend fun deleteSubject(id: String): Result<Unit>
}