package ru.mirea.shylit.studydeadline.presentation.subjects

import ru.mirea.shylit.studydeadline.domain.models.Subject

data class SubjectsUiState(
    val subjects: List<Subject> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val subjectName: String = "",
    val isAddDialogVisible: Boolean = false,
    val editingSubject: Subject? = null,
    val subjectDescription: String = ""
)