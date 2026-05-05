package ru.mirea.shylit.studydeadline.presentation.subjects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.shylit.studydeadline.domain.models.Subject

@Composable
fun SubjectsScreen(
    viewModel: SubjectsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::showAddDialog
            ) {
                Text("+")
            }
        }
    ) { paddingValues ->
        SubjectsContent(
            uiState = uiState,
            modifier = Modifier.padding(paddingValues)
        )
    }

    if (uiState.isAddDialogVisible) {
        AddSubjectDialog(
            subjectName = uiState.subjectName,
            onSubjectNameChange = viewModel::onSubjectNameChange,
            onDismiss = viewModel::hideAddDialog,
            onConfirm = viewModel::createSubject
        )
    }
}

@Composable
private fun SubjectsContent(
    uiState: SubjectsUiState,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading && uiState.subjects.isEmpty() -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Загрузка предметов...",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        uiState.subjects.isEmpty() -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Предметов пока нет",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Нажмите «+», чтобы добавить первый предмет",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.subjects) { subject ->
                    SubjectCard(subject = subject)
                }
            }
        }
    }
}

@Composable
private fun SubjectCard(
    subject: Subject
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = subject.name,
                style = MaterialTheme.typography.titleMedium
            )

            subject.createdAt?.let {
                Text(
                    text = "Создано: $it",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun AddSubjectDialog(
    subjectName: String,
    onSubjectNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Добавить предмет")
        },
        text = {
            OutlinedTextField(
                value = subjectName,
                onValueChange = onSubjectNameChange,
                label = {
                    Text("Название предмета")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Отмена")
            }
        }
    )
}