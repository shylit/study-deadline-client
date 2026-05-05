package ru.mirea.shylit.studydeadline.domain.usecases.theme

import ru.mirea.shylit.studydeadline.domain.repositories.ThemeRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.setDarkTheme(enabled)
    }
}