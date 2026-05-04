package ru.mirea.shylit.studydeadline.data.remote.dto

data class TaskDto(
    val id: String,
    val subjectId: String,
    val title: String,
    val description: String? = null,
    val deadline: String? = null,
    val type: String,
    val priority: String,
    val status: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)