package ru.mirea.shylit.studydeadline.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.shylit.studydeadline.domain.models.StudyTask

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Поиск",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = uiState.query,
            onValueChange = viewModel::onQueryChange,
            label = {
                Text("Введите название задания")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            singleLine = true,
            trailingIcon = {
                if (uiState.query.isNotBlank()) {
                    TextButton(
                        onClick = {
                            viewModel.clearQuery()
                            focusManager.clearFocus()
                        }
                    ) {
                        Text("Очистить")
                    }
                }
            }
        )

        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.search()
            },
            enabled = uiState.query.isNotBlank() && !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text("Найти")
        }

        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 24.dp)
                )
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage ?: "Ошибка поиска",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 24.dp)
                )

                TextButton(
                    onClick = viewModel::repeatSearch
                ) {
                    Text("Обновить")
                }
            }

            uiState.results.isNotEmpty() -> {
                SearchResults(
                    results = uiState.results,
                    onResultClick = viewModel::addCurrentQueryToHistory,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            uiState.hasSearched -> {
                Text(
                    text = "Ничего не найдено",
                    modifier = Modifier.padding(top = 24.dp)
                )
            }

            uiState.history.isNotEmpty() -> {
                SearchHistory(
                    history = uiState.history,
                    onHistoryClick = viewModel::onHistoryClick,
                    onClearHistoryClick = viewModel::clearHistory,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchHistory(
    history: List<String>,
    onHistoryClick: (String) -> Unit,
    onClearHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "История поиска",
                style = MaterialTheme.typography.titleMedium
            )

            TextButton(
                onClick = onClearHistoryClick
            ) {
                Text("Очистить историю")
            }
        }

        history.forEach { query ->
            Text(
                text = query,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onHistoryClick(query)
                    }
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun SearchResults(
    results: List<StudyTask>,
    onResultClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(results) { task ->
            SearchTaskCard(
                task = task,
                onClick = onResultClick
            )
        }
    }
}

@Composable
private fun SearchTaskCard(
    task: StudyTask,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
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
        }
    }
}