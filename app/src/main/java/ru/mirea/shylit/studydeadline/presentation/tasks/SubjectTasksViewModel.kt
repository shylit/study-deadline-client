package ru.mirea.shylit.studydeadline.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.GetTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.RefreshTasksBySubjectUseCase
import javax.inject.Inject
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.UpdateTaskStatusUseCase

@HiltViewModel
class SubjectTasksViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val refreshTasksBySubjectUseCase: RefreshTasksBySubjectUseCase
) : ViewModel() {

    private val localState = MutableStateFlow(SubjectTasksUiState())

    val uiState = combine(
        localState,
        getTasksUseCase()
    ) { state, tasks ->
        state.copy(
            tasks = tasks.filter { it.subjectId == state.subjectName }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SubjectTasksUiState()
    )

    fun loadSubject(subjectName: String) {
        localState.value = localState.value.copy(subjectName = subjectName)

        viewModelScope.launch {
            localState.value = localState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                refreshTasksBySubjectUseCase(subjectName)
            }.onFailure { error ->
                localState.value = localState.value.copy(
                    errorMessage = error.message ?: "Не удалось загрузить задачи предмета"
                )
            }

            localState.value = localState.value.copy(isLoading = false)
        }
    }

    fun markTaskCompleted(taskId: String) {
        val subjectName = uiState.value.subjectName

        viewModelScope.launch {
            updateTaskStatusUseCase(
                id = taskId,
                status = TaskStatus.COMPLETED
            )

            loadSubject(subjectName)
        }
    }
}