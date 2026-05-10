package ru.mirea.shylit.studydeadline.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.mirea.shylit.studydeadline.domain.models.StudyTask
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType

interface TaskRepository {

    fun getTasks(): Flow<List<StudyTask>>

    fun getTasksBySubject(subjectId: String): Flow<List<StudyTask>>

    suspend fun refreshTasks()

    suspend fun createTask(
        title: String,
        description: String,
        subject: String,
        deadline: String,
        priority: TaskPriority,
        type: TaskType
    ): Result<StudyTask>

    suspend fun updateTask(
        id: String,
        subjectId: String,
        title: String,
        description: String?,
        deadline: String?,
        type: TaskType,
        priority: TaskPriority,
        status: TaskStatus
    ): Result<StudyTask>

    suspend fun updateTaskStatus(
        id: String,
        status: TaskStatus
    ): Result<StudyTask>

    suspend fun deleteTask(id: String): Result<Unit>

    suspend fun searchTasks(query: String): Result<List<StudyTask>>

    suspend fun refreshTodayTasks()

    suspend fun refreshTasksBySubject(subjectName: String)
}