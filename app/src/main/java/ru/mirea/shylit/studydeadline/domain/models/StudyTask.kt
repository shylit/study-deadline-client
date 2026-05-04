package ru.mirea.shylit.studydeadline.domain.models

data class StudyTask(
    val id: String,
    val subjectId: String,
    val title: String,
    val description: String? = null,
    val deadline: String? = null,
    val type: TaskType,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdAt: String? = null,
    val updatedAt: String? = null
)