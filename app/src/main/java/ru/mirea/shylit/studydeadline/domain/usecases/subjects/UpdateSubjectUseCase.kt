package ru.mirea.shylit.studydeadline.domain.usecases.subjects

import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository
import javax.inject.Inject

class UpdateSubjectUseCase @Inject constructor(
    private val repository: SubjectRepository
) {
    suspend operator fun invoke(
        id: String,
        name: String,
        description: String
    ) = repository.updateSubject(id, name, description)
}