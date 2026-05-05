package ru.mirea.shylit.studydeadline.presentation.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.usecases.subjects.CreateSubjectUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.subjects.GetSubjectsUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.subjects.RefreshSubjectsUseCase
import javax.inject.Inject

@HiltViewModel
class SubjectsViewModel @Inject constructor(
    getSubjectsUseCase: GetSubjectsUseCase,
    private val refreshSubjectsUseCase: RefreshSubjectsUseCase,
    private val createSubjectUseCase: CreateSubjectUseCase
) : ViewModel() {

    private val localState = MutableStateFlow(SubjectsUiState())

    val uiState = combine(
        localState,
        getSubjectsUseCase()
    ) { state, subjects ->
        state.copy(subjects = subjects)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SubjectsUiState()
    )

    init {
        refreshSubjects()
    }

    fun onSubjectNameChange(value: String) {
        localState.value = localState.value.copy(subjectName = value)
    }

    fun showAddDialog() {
        localState.value = localState.value.copy(
            isAddDialogVisible = true,
            subjectName = "",
            errorMessage = null
        )
    }

    fun hideAddDialog() {
        localState.value = localState.value.copy(
            isAddDialogVisible = false,
            subjectName = ""
        )
    }

    fun refreshSubjects() {
        viewModelScope.launch {
            localState.value = localState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            runCatching {
                refreshSubjectsUseCase()
            }.onFailure { error ->
                localState.value = localState.value.copy(
                    errorMessage = error.message ?: "Не удалось загрузить предметы"
                )
            }

            localState.value = localState.value.copy(isLoading = false)
        }
    }

    fun createSubject() {
        val name = uiState.value.subjectName.trim()

        if (name.isBlank()) {
            localState.value = localState.value.copy(
                errorMessage = "Введите название предмета"
            )
            return
        }

        viewModelScope.launch {
            localState.value = localState.value.copy(isLoading = true)

            createSubjectUseCase(name = name)
                .onSuccess {
                    localState.value = localState.value.copy(
                        isLoading = false,
                        isAddDialogVisible = false,
                        subjectName = "",
                        errorMessage = null
                    )
                }
                .onFailure { error ->
                    localState.value = localState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Не удалось создать предмет"
                    )
                }
        }
    }
}