package ru.mirea.shylit.studydeadline.data.remote.dto

data class SubjectDto(
    val id: Int,
    val name: String,
    val description: String? = null
)