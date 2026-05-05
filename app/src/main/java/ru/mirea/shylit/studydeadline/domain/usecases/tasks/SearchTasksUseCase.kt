package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class SearchTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(query: String) = repository.searchTasks(query)
}