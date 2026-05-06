package ru.mirea.shylit.studydeadline.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.GetTasksUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.RefreshTasksUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val refreshTasksUseCase: RefreshTasksUseCase
) : ViewModel() {

    private val localState = MutableStateFlow(TodayUiState())

    val uiState = combine(
        localState,
        getTasksUseCase()
    ) { state, tasks ->
        val today = LocalDate.now()
        val activeTasks = tasks.filter { it.status != TaskStatus.DONE }

        state.copy(
            todayTasks = activeTasks.filter { task ->
                task.deadline?.let { deadline ->
                    runCatching {
                        LocalDate.parse(deadline) == today
                    }.getOrDefault(false)
                } == true
            },
            overdueTasks = activeTasks.filter { task ->
                task.deadline?.let { deadline ->
                    runCatching {
                        LocalDate.parse(deadline).isBefore(today)
                    }.getOrDefault(false)
                } == true
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TodayUiState()
    )

    init {
        refreshTodayTasks()
    }

    fun refreshTodayTasks() {
        viewModelScope.launch {
            localState.value = localState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                refreshTasksUseCase()
            }.onFailure { error ->
                localState.value = localState.value.copy(
                    errorMessage = error.message ?: "Не удалось загрузить задачи"
                )
            }

            localState.value = localState.value.copy(isLoading = false)
        }
    }
}