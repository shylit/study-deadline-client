package ru.mirea.shylit.studydeadline.data.remote.dto

data class SubjectDto(
    val id: String,
    val name: String,
    val color: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)