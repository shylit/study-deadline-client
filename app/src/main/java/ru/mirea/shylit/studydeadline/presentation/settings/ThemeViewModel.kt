package ru.mirea.shylit.studydeadline.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.mirea.shylit.studydeadline.domain.usecases.theme.ObserveDarkThemeUseCase
import ru.mirea.shylit.studydeadline.domain.usecases.theme.SetDarkThemeUseCase
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    observeDarkThemeUseCase: ObserveDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase
) : ViewModel() {

    val isDarkTheme = observeDarkThemeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(enabled)
        }
    }
}