package ru.mirea.shylit.studydeadline.presentation.tasks.create

import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.models.Subject

data class CreateTaskUiState(
    val title: String = "",
    val description: String = "",
    val subject: String = "",
    val deadline: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val type: TaskType = TaskType.OTHER,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val subjects: List<Subject> = emptyList(),
    val isSubjectMenuExpanded: Boolean = false
)