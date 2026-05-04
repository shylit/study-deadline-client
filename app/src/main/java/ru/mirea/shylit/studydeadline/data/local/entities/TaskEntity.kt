package ru.mirea.shylit.studydeadline.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val subjectId: String,
    val title: String,
    val description: String?,
    val deadline: String?,
    val type: String,
    val priority: String,
    val status: String,
    val createdAt: String?,
    val updatedAt: String?
)