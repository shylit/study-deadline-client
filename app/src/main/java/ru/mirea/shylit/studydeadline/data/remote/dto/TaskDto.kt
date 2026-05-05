package ru.mirea.shylit.studydeadline.data.remote.dto

data class TaskDto(
    val id: Int,
    val title: String,
    val description: String? = null,
    val subject: String,
    val deadline: String? = null,
    val status: String,
    val priority: String,
    val type: String
)