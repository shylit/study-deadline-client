package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        subjectId: String,
        title: String,
        description: String?,
        deadline: String?,
        type: TaskType,
        priority: TaskPriority,
        status: TaskStatus
    ) = repository.createTask(
        subjectId = subjectId,
        title = title,
        description = description,
        deadline = deadline,
        type = type,
        priority = priority,
        status = status
    )
}