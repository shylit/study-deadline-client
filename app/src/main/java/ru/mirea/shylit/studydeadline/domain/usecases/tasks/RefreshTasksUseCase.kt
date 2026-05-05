package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        repository.refreshTasks()
    }

    suspend fun today() {
        repository.refreshTodayTasks()
    }
}