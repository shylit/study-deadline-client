package ru.mirea.shylit.studydeadline.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mirea.shylit.studydeadline.data.local.dao.TaskDao
import ru.mirea.shylit.studydeadline.data.mappers.toApiValue
import ru.mirea.shylit.studydeadline.data.mappers.toDomain
import ru.mirea.shylit.studydeadline.data.mappers.toEntity
import ru.mirea.shylit.studydeadline.data.remote.api.TaskApi
import ru.mirea.shylit.studydeadline.data.remote.dto.CreateTaskRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.UpdateTaskRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.UpdateTaskStatusRequest
import ru.mirea.shylit.studydeadline.domain.models.StudyTask
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskApi: TaskApi,
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getTasks(): Flow<List<StudyTask>> {
        return taskDao.observeTasks()
            .map { tasks -> tasks.map { it.toDomain() } }
    }

    override fun getTasksBySubject(subjectId: String): Flow<List<StudyTask>> {
        return taskDao.observeTasksBySubject(subjectId)
            .map { tasks -> tasks.map { it.toDomain() } }
    }

    override suspend fun refreshTasks() {
        val remoteTasks = taskApi.getTasks()
            .map { it.toDomain() }

        taskDao.insertTasks(remoteTasks.map { it.toEntity() })
    }

    override suspend fun createTask(
        subjectId: String,
        title: String,
        description: String?,
        deadline: String?,
        type: TaskType,
        priority: TaskPriority,
        status: TaskStatus
    ): Result<StudyTask> {
        return runCatching {
            val task = taskApi.createTask(
                CreateTaskRequest(
                    subjectId = subjectId,
                    title = title,
                    description = description,
                    deadline = deadline,
                    type = type.toApiValue(),
                    priority = priority.toApiValue(),
                    status = status.toApiValue()
                )
            ).toDomain()

            taskDao.insertTask(task.toEntity())
            task
        }
    }

    override suspend fun updateTask(
        id: String,
        subjectId: String,
        title: String,
        description: String?,
        deadline: String?,
        type: TaskType,
        priority: TaskPriority,
        status: TaskStatus
    ): Result<StudyTask> {
        return runCatching {
            val task = taskApi.updateTask(
                id = id,
                request = UpdateTaskRequest(
                    subjectId = subjectId,
                    title = title,
                    description = description,
                    deadline = deadline,
                    type = type.toApiValue(),
                    priority = priority.toApiValue(),
                    status = status.toApiValue()
                )
            ).toDomain()

            taskDao.insertTask(task.toEntity())
            task
        }
    }

    override suspend fun updateTaskStatus(
        id: String,
        status: TaskStatus
    ): Result<StudyTask> {
        return runCatching {
            val task = taskApi.updateTaskStatus(
                id = id,
                request = UpdateTaskStatusRequest(
                    status = status.toApiValue()
                )
            ).toDomain()

            taskDao.insertTask(task.toEntity())
            task
        }
    }

    override suspend fun deleteTask(id: String): Result<Unit> {
        return runCatching {
            taskApi.deleteTask(id)
            taskDao.deleteTask(id)
        }
    }

    override suspend fun searchTasks(query: String): Result<List<StudyTask>> {
        return runCatching {
            taskApi.searchTasks(query)
                .map { it.toDomain() }
        }
    }

    override suspend fun refreshTodayTasks() {
        val remoteTasks = taskApi.getTodayTasks()
            .map { it.toDomain() }

        taskDao.insertTasks(remoteTasks.map { it.toEntity() })
    }
}