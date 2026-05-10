package ru.mirea.shylit.studydeadline.core.ui

import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType

fun TaskPriority.toRuLabel(): String {
    return when (this) {
        TaskPriority.LOW -> "Низкий"
        TaskPriority.MEDIUM -> "Средний"
        TaskPriority.HIGH -> "Высокий"
    }
}

fun TaskStatus.toRuLabel(): String {
    return when (this) {
        TaskStatus.PLANNED -> "В планах"
        TaskStatus.IN_PROGRESS -> "В работе"
        TaskStatus.COMPLETED -> "Выполнено"
        TaskStatus.OVERDUE -> "Просрочено"
    }
}

fun TaskType.toRuLabel(): String {
    return when (this) {
        TaskType.HOMEWORK -> "Домашнее задание"
        TaskType.LAB_WORK -> "Лабораторная"
        TaskType.COURSE_WORK -> "Курсовая"
        TaskType.EXAM -> "Экзамен"
        TaskType.TEST -> "Тест"
        TaskType.OTHER -> "Другое"
    }
}