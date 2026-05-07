package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class RefreshTasksBySubjectUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(subjectName: String) {
        repository.refreshTasksBySubject(subjectName)
    }
}