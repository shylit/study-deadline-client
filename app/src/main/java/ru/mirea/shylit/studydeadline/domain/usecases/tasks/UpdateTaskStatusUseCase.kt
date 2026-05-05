package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        id: String,
        status: TaskStatus
    ) = repository.updateTaskStatus(id, status)
}