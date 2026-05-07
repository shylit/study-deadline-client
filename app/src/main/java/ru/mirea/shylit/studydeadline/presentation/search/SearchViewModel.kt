package ru.mirea.shylit.studydeadline.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.usecases.search.AddSearchQueryUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.search.ClearSearchHistoryUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.search.GetSearchHistoryUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.tasks.SearchTasksUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTasksUseCase: SearchTasksUseCase,
    private val addSearchQueryUseCase: AddSearchQueryUseCase,
    private val clearSearchHistoryUseCase: ClearSearchHistoryUseCase,
    getSearchHistoryUseCase: GetSearchHistoryUseCase
) : ViewModel() {

    private val localState = MutableStateFlow(SearchUiState())

    val uiState = combine(
        localState,
        getSearchHistoryUseCase()
    ) { state, history ->
        state.copy(history = history)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState()
    )

    fun onQueryChange(value: String) {
        localState.value = localState.value.copy(
            query = value,
            errorMessage = null
        )
    }

    fun clearQuery() {
        localState.value = localState.value.copy(
            query = "",
            results = emptyList(),
            errorMessage = null,
            hasSearched = false
        )
    }

    fun search() {
        val query = uiState.value.query.trim()

        if (query.isBlank()) return

        viewModelScope.launch {
            localState.value = localState.value.copy(
                isLoading = true,
                errorMessage = null,
                hasSearched = true
            )

            searchTasksUseCase(query)
                .onSuccess { tasks ->
                    addSearchQueryUseCase(query)

                    localState.value = localState.value.copy(
                        results = tasks,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    localState.value = localState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Не удалось выполнить поиск"
                    )
                }
        }
    }

    fun repeatSearch() {
        search()
    }

    fun onHistoryClick(query: String) {
        localState.value = localState.value.copy(query = query)
        search()
    }

    fun clearHistory() {
        viewModelScope.launch {
            clearSearchHistoryUseCase()
        }
    }

    fun addCurrentQueryToHistory() {
        val query = uiState.value.query.trim()

        if (query.isBlank()) return

        viewModelScope.launch {
            addSearchQueryUseCase(query)
        }
    }
}