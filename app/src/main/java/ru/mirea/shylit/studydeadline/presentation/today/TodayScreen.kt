package ru.mirea.shylit.studydeadline.presentation.today

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

@Composable
fun TodayScreen(
    onCreateTaskClick: () -> Unit,
    viewModel: TodayViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTaskClick
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->
        when {
            uiState.isLoading &&
                    uiState.todayTasks.isEmpty() &&
                    uiState.overdueTasks.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Загрузка задач...",
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }

            uiState.errorMessage != null &&
                    uiState.todayTasks.isEmpty() &&
                    uiState.overdueTasks.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.errorMessage ?: "Не удалось загрузить задачи",
                        color = MaterialTheme.colorScheme.error
                    )

                    TextButton(
                        onClick = viewModel::refreshTodayTasks,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Обновить")
                    }
                }
            }

            uiState.todayTasks.isEmpty() && uiState.overdueTasks.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "На сегодня задач нет",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Нажмите «+», чтобы добавить задачу",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (uiState.overdueTasks.isNotEmpty()) {
                        item {
                            Text(
                                text = "Просрочено",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        items(uiState.overdueTasks) { task ->
                            TodayTaskCard(task = task)
                        }
                    }

                    if (uiState.todayTasks.isNotEmpty()) {
                        item {
                            Text(
                                text = "Сегодня",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }

                        items(uiState.todayTasks) { task ->
                            TodayTaskCard(task = task)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TodayTaskCard(
    task: StudyTask
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
    }
}