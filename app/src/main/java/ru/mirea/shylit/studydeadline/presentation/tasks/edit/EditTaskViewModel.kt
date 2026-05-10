package ru.mirea.shylit.studydeadline.presentation.tasks.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.models.StudyTask
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.UpdateTaskUseCase
import javax.inject.Inject
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.GetTaskByIdUseCase

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTaskUiState())
    val uiState = _uiState.asStateFlow()

    fun setTask(task: StudyTask) {
        _uiState.value = EditTaskUiState(
            id = task.id,
            title = task.title,
            description = task.description.orEmpty(),
            subject = task.subjectId,
            deadline = task.deadline.orEmpty(),
            status = task.status,
            priority = task.priority,
            type = task.type
        )
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

    fun onDeadlineChange(value: String) {
        _uiState.value = _uiState.value.copy(deadline = value, errorMessage = null)
    }

    fun onStatusChange(value: TaskStatus) {
        _uiState.value = _uiState.value.copy(status = value)
    }

    fun onPriorityChange(value: TaskPriority) {
        _uiState.value = _uiState.value.copy(priority = value)
    }

    fun onTypeChange(value: TaskType) {
        _uiState.value = _uiState.value.copy(type = value)
    }

    fun updateTask() {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Введите название задачи")
            return
        }

        if (state.subject.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Введите предмет")
            return
        }

        if (state.deadline.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Введите дедлайн")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMessage = null)

            updateTaskUseCase(
                id = state.id,
                title = state.title.trim(),
                description = state.description.trim(),
                subject = state.subject.trim(),
                deadline = state.deadline.trim(),
                status = state.status,
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
                    errorMessage = error.message ?: "Не удалось обновить задачу"
                )
            }
        }
    }
    fun loadTask(taskId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val task = getTaskByIdUseCase(taskId)

            if (task == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Задача не найдена"
                )
                return@launch
            }

            setTask(task)

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}