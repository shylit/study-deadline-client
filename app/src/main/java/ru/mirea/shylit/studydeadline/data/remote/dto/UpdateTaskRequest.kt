package ru.mirea.shylit.studydeadline.data.remote.dto

data class UpdateTaskRequest(
    val title: String,
    val description: String,
    val subject: String,
    val deadline: String,
    val status: String,
    val priority: String,
    val type: String
)