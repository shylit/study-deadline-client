package ru.mirea.shylit.studydeadline.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val color: String?,
    val createdAt: String?,
    val updatedAt: String?
)