package ru.mirea.shylit.studydeadline.presentation.tasks.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.shylit.studydeadline.domain.models.TaskPriority
import ru.mirea.shylit.studydeadline.domain.models.TaskStatus
import ru.mirea.shylit.studydeadline.domain.models.TaskType
import ru.mirea.shylit.studydeadline.core.ui.toRuLabel

@Composable
fun EditTaskScreen(
    taskId: String,
    onBackClick: () -> Unit,
    onTaskUpdated: () -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onTaskUpdated()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TextButton(
            onClick = onBackClick
        ) {
            Text("Назад")
        }

        Text(
            text = "Редактирование задачи",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("Название") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Описание") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        OutlinedTextField(
            value = uiState.subject,
            onValueChange = viewModel::onSubjectChange,
            label = { Text("Предмет") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.deadline,
            onValueChange = viewModel::onDeadlineChange,
            label = { Text("Дедлайн, например 2026-06-10") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            singleLine = true
        )

        Text(
            text = "Статус",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        TaskStatus.entries.forEach { status ->
            TextButton(
                onClick = {
                    viewModel.onStatusChange(status)
                }
            ) {
                Text(
                    text = if (uiState.status == status) {
                        "✓ ${status.toRuLabel()}"
                    } else {
                        status.toRuLabel()
                    }
                )
            }
        }

        Text(
            text = "Приоритет",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        TaskPriority.entries.forEach { priority ->
            TextButton(
                onClick = {
                    viewModel.onPriorityChange(priority)
                }
            ) {
                Text(
                    text = if (uiState.priority == priority) {
                        "✓ ${priority.toRuLabel()}"
                    } else {
                        priority.toRuLabel()
                    }
                )
            }
        }

        Text(
            text = "Тип задачи",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        TaskType.entries.forEach { type ->
            TextButton(
                onClick = {
                    viewModel.onTypeChange(type)
                }
            ) {
                Text(
                    text = if (uiState.type == type) {
                        "✓ ${type.toRuLabel()}"
                    } else {
                        type.toRuLabel()
                    }
                )
            }
        }

        uiState.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Button(
            onClick = viewModel::updateTask,
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Сохранить изменения")
            }
        }
    }
}