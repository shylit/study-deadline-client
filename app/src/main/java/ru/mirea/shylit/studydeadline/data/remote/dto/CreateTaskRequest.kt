package ru.mirea.shylit.studydeadline.data.remote.dto

data class CreateTaskRequest(
    val title: String,
    val description: String,
    val subject: String,
    val deadline: String,
    val priority: String = "MEDIUM",
    val type: String = "OTHER"
)