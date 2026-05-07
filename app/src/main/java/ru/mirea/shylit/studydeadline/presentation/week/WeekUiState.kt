package ru.mirea.shylit.studydeadline.presentation.week

import ru.mirea.shylit.studydeadline.domain.models.StudyTask

data class WeekUiState(
    val tasksByDay: Map<String, List<StudyTask>> = emptyMap(),
    val tasksWithoutDeadline: List<StudyTask> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)