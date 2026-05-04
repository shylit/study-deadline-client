package ru.mirea.shylit.studydeadline.data.remote.dto

data class CreateSubjectRequest(
    val name: String,
    val color: String? = null
)