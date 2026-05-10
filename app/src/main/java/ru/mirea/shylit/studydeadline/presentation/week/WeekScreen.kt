package ru.mirea.shylit.studydeadline.presentation.week

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.shylit.studydeadline.domain.models.StudyTask
import androidx.compose.material3.TextButton
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus

@Composable
fun WeekScreen(
    viewModel: WeekViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading &&
                uiState.tasksByDay.isEmpty() &&
                uiState.tasksWithoutDeadline.isEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Загрузка задач на неделю...",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        uiState.errorMessage != null &&
                uiState.tasksByDay.isEmpty() &&
                uiState.tasksWithoutDeadline.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uiState.errorMessage ?: "Не удалось загрузить задачи",
                    color = MaterialTheme.colorScheme.error
                )

                TextButton(
                    onClick = viewModel::refreshTasks,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Обновить")
                }
            }
        }

        uiState.tasksByDay.isEmpty() && uiState.tasksWithoutDeadline.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "На этой неделе задач нет",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Добавьте задачи в разделе «Предметы»",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.tasksByDay.forEach { (day, tasks) ->
                    item {
                        Text(
                            text = day,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    items(tasks) { task ->
                        WeekTaskCard(
                            task = task,
                            onMarkCompletedClick = {
                                viewModel.markTaskCompleted(task.id)
                            },
                            onDeleteClick = {
                                viewModel.deleteTask(task.id)
                            }
                        )
                    }
                }

                if (uiState.tasksWithoutDeadline.isNotEmpty()) {
                    item {
                        Text(
                            text = "Без даты",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }

                    items(uiState.tasksWithoutDeadline) { task ->
                        WeekTaskCard(
                            task = task,
                            onMarkCompletedClick = {
                                viewModel.markTaskCompleted(task.id)
                            },
                            onDeleteClick = {
                                viewModel.deleteTask(task.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeekTaskCard(
    task: StudyTask,
    onMarkCompletedClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Предмет: ${task.subjectId}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            task.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Text(
                text = "Дедлайн: ${task.deadline ?: "без даты"}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Приоритет: ${task.priority}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Статус: ${task.status}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (task.status != TaskStatus.COMPLETED) {
            TextButton(
                onClick = onMarkCompletedClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Отметить выполненной")
            }
        }

        TextButton(
            onClick = onDeleteClick,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text("Удалить")
        }
    }
}