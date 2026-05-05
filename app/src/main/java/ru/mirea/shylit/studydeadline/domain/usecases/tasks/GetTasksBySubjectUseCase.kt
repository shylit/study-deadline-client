package ru.mirea.shylit.studydeadline.domain.usecases.tasks

import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Inject

class GetTasksBySubjectUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(subjectId: String) = repository.getTasksBySubject(subjectId)
}