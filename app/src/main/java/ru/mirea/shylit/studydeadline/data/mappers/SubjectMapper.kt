package ru.mirea.shylit.studydeadline.data.mappers

import ru.mirea.shylit.studydeadline.data.remote.dto.SubjectDto
import ru.mirea.shylit.studydeadline.domain.models.Subject

fun SubjectDto.toDomain(): Subject {
    return Subject(
        id = id,
        name = name,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}