package ru.mirea.shylit.studydeadline.presentation.search

import ru.mirea.shylit.studydeadline.domain.models.StudyTask

data class SearchUiState(
    val query: String = "",
    val results: List<StudyTask> = emptyList(),
    val history: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false
)