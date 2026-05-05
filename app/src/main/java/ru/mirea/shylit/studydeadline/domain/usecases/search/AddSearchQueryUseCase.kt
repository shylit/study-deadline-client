package ru.mirea.shylit.studydeadline.domain.usecases.search

import ru.mirea.shylit.studydeadline.domain.repositories.SearchHistoryRepository
import javax.inject.Inject

class AddSearchQueryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(query: String) {
        repository.addQuery(query)
    }
}