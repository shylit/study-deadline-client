package ru.mirea.shylit.studydeadline.presentation.tasks

import ru.mirea.shylit.studydeadline.domain.models.StudyTask

data class SubjectTasksUiState(
    val subjectName: String = "",
    val tasks: List<StudyTask> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)