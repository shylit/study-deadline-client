package ru.mirea.shylit.studydeadline.data.mappers

import ru.mirea.shylit.studydeadline.data.remote.dto.TaskDto
import ru.mirea.shylit.studydeadline.domain.models.StudyTask
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.data.local.entities.TaskEntity

fun TaskDto.toDomain(): StudyTask {
    return StudyTask(
        id = id.toString(),
        subjectId = subject,
        title = title,
        description = description,
        deadline = deadline,
        type = type.toTaskType(),
        priority = priority.toTaskPriority(),
        status = status.toTaskStatus()
    )
}

fun TaskType.toApiValue(): String = name

fun TaskPriority.toApiValue(): String = name

fun TaskStatus.toApiValue(): String = name

private fun String.toTaskType(): TaskType {
    return when (this) {
        "HOMEWORK" -> TaskType.HOMEWORK
        "LAB_WORK", "LAB" -> TaskType.LAB_WORK
        "COURSE_WORK", "COURSEWORK" -> TaskType.COURSE_WORK
        "EXAM" -> TaskType.EXAM
        "TEST" -> TaskType.TEST
        else -> TaskType.OTHER
    }
}
private fun String.toTaskPriority(): TaskPriority {
    return runCatching {
        TaskPriority.valueOf(this)
    }.getOrDefault(TaskPriority.MEDIUM)
}

private fun String.toTaskStatus(): TaskStatus {
    return when (this) {
        "PLANNED" -> TaskStatus.PLANNED
        "IN_PROGRESS" -> TaskStatus.IN_PROGRESS
        "COMPLETED", "DONE" -> TaskStatus.COMPLETED
        "OVERDUE" -> TaskStatus.OVERDUE
        else -> TaskStatus.PLANNED
    }
}

fun TaskEntity.toDomain(): StudyTask {
    return StudyTask(
        id = id,
        subjectId = subjectId,
        title = title,
        description = description,
        deadline = deadline,
        type = type.toTaskType(),
        priority = priority.toTaskPriority(),
        status = status.toTaskStatus(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun StudyTask.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        subjectId = subjectId,
        title = title,
        description = description,
        deadline = deadline,
        type = type.toApiValue(),
        priority = priority.toApiValue(),
        status = status.toApiValue(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}