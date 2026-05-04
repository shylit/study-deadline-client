package ru.mirea.shylit.studydeadline.data.mappers

import ru.mirea.shylit.studydeadline.data.remote.dto.SubjectDto
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.data.local.entities.SubjectEntity

fun SubjectDto.toDomain(): Subject {
    return Subject(
        id = id,
        name = name,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun SubjectEntity.toDomain(): Subject {
    return Subject(
        id = id,
        name = name,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Subject.toEntity(): SubjectEntity {
    return SubjectEntity(
        id = id,
        name = name,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}