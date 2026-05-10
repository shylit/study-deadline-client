package ru.mirea.shylit.studydeadline.presentation.tasks.edit

import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType

data class EditTaskUiState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val subject: String = "",
    val deadline: String = "",
    val status: TaskStatus = TaskStatus.PLANNED,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val type: TaskType = TaskType.OTHER,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)