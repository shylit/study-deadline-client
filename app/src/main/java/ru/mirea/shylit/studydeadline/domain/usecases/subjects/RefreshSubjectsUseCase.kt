package ru.mirea.shylit.studydeadline.domain.usecases.subjects

import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository
import javax.inject.Inject

class RefreshSubjectsUseCase @Inject constructor(
    private val repository: SubjectRepository
) {
    suspend operator fun invoke() = repository.refreshSubjects()
}