package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String) = repository.deleteTask(id)
}