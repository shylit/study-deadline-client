package ru.mirea.shylit.studydeadline.domain.usecases.subjects

import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val repository: SubjectRepository
) {
    operator fun invoke() = repository.getSubjects()
}