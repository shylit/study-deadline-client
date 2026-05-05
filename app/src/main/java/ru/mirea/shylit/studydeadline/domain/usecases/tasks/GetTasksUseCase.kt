package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getTasks()
}