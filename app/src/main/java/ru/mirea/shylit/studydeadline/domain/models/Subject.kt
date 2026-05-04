package ru.mirea.shylit.studydeadline.domain.models

data class Subject(
    val id: String,
    val name: String,
    val color: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)