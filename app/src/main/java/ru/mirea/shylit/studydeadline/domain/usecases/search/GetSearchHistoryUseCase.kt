package ru.mirea.shylit.studydeadline.domain.usecases.search

import ru.mirea.shylit.studydeadline.domain.repositories.SearchHistoryRepository
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    operator fun invoke() = repository.getSearchHistory()
}