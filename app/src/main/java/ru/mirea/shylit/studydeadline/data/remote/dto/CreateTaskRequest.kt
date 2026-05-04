package ru.mirea.shylit.studydeadline.data.remote.dto

data class CreateTaskRequest(
    val subjectId: String,
    val title: String,
    val description: String?,
    val deadline: String?,
    val type: String,
    val priority: String,
    val status: String
)