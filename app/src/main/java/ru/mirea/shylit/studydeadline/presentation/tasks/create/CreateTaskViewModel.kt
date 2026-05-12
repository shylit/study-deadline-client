package ru.mirea.shylit.studydeadline.presentation.tasks.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.usecases.subjects.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.CreateTaskUseCase
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    getSubjectsUseCase: GetSubjectsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSubjectsUseCase().collect { subjects ->
                _uiState.value = _uiState.value.copy(subjects = subjects)
            }
        }
    }

    fun onTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(title = value, errorMessage = null)
    }

    fun onDescriptionChange(value: String) {
        _uiState.value = _uiState.value.copy(description = value, errorMessage = null)
    }

    fun onSubjectChange(value: String) {
        _uiState.value = _uiState.value.copy(subject = value, errorMessage = null)
    }

    fun showSubjectMenu() {
        _uiState.value = _uiState.value.copy(isSubjectMenuExpanded = true)
    }

    fun hideSubjectMenu() {
        _uiState.value = _uiState.value.copy(isSubjectMenuExpanded = false)
    }

    fun selectSubject(subjectName: String) {
        _uiState.value = _uiState.value.copy(
            subject = subjectName,
            isSubjectMenuExpanded = false,
            errorMessage = null
        )
    }

    fun onDeadlineChange(value: String) {
        _uiState.value = _uiState.value.copy(deadline = value, errorMessage = null)
    }

    fun onPriorityChange(value: TaskPriority) {
        _uiState.value = _uiState.value.copy(priority = value)
    }

    fun onTypeChange(value: TaskType) {
        _uiState.value = _uiState.value.copy(type = value)
    }

    fun createTask() {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Введите название задачи")
            return
        }

        if (state.subject.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Выберите предмет")
            return
        }

        if (state.deadline.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Введите дедлайн в формате ГГГГ-ММ-ДД")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(
                isLoading = true,
                errorMessage = null
            )

            createTaskUseCase(
                title = state.title.trim(),
                description = state.description.trim(),
                subject = state.subject.trim(),
                deadline = state.deadline.trim(),
                priority = state.priority,
                type = state.type
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error.message ?: "Не удалось создать задачу"
                )
            }
        }
    }
}