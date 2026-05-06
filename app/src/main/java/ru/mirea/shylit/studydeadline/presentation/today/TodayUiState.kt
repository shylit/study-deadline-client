package ru.mirea.shylit.studydeadline.presentation.today

import ru.mirea.shylit.studydeadline.domain.models.StudyTask

data class TodayUiState(
    val todayTasks: List<StudyTask> = emptyList(),
    val overdueTasks: List<StudyTask> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)