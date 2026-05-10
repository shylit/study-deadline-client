package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        id: String,
        title: String,
        description: String,
        subject: String,
        deadline: String,
        status: TaskStatus,
        priority: TaskPriority,
        type: TaskType
    ) = repository.updateTask(
        id = id,
        title = title,
        description = description,
        subject = subject,
        deadline = deadline,
        status = status,
        priority = priority,
        type = type
    )
}