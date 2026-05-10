package ru.mirea.shylit.studydeadline.presentation.week

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
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.UpdateTaskStatusUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.DeleteTaskUseCase

@HiltViewModel
class WeekViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val refreshTasksUseCase: RefreshTasksUseCase
) : ViewModel() {

    private val localState = MutableStateFlow(WeekUiState())

    val uiState = combine(
        localState,
        getTasksUseCase()
    ) { state, tasks ->
        val today = LocalDate.now()
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val endOfWeek = today.with(DayOfWeek.SUNDAY)

        val activeTasks = tasks.filter { it.status != TaskStatus.COMPLETED }

        val weekTasks = activeTasks.filter { task ->
            task.deadline?.let { deadline ->
                runCatching {
                    val date = LocalDate.parse(deadline)
                    !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek)
                }.getOrDefault(false)
            } == true
        }

        val tasksByDay = weekTasks.groupBy { task ->
            val date = LocalDate.parse(task.deadline)
            dayName(date.dayOfWeek)
        }

        val withoutDeadline = activeTasks.filter { it.deadline == null }

        state.copy(
            tasksByDay = tasksByDay,
            tasksWithoutDeadline = withoutDeadline
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WeekUiState()
    )

    init {
        refreshTasks()
    }

    fun refreshTasks() {
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

    private fun dayName(dayOfWeek: DayOfWeek): String {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> "Понедельник"
            DayOfWeek.TUESDAY -> "Вторник"
            DayOfWeek.WEDNESDAY -> "Среда"
            DayOfWeek.THURSDAY -> "Четверг"
            DayOfWeek.FRIDAY -> "Пятница"
            DayOfWeek.SATURDAY -> "Суббота"
            DayOfWeek.SUNDAY -> "Воскресенье"
        }
    }

    fun markTaskCompleted(taskId: String) {
        viewModelScope.launch {
            updateTaskStatusUseCase(
                id = taskId,
                status = TaskStatus.COMPLETED
            )

            refreshTasks()
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
            refreshTasks()
        }
    }
}