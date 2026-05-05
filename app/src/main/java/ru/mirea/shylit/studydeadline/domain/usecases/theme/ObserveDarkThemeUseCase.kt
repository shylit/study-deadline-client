package ru.mirea.shylit.studydeadline.domain.usecases.theme

import ru.mirea.shylit.studydeadline.domain.repositories.ThemeRepository
import javax.inject.Inject

class ObserveDarkThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke() = repository.observeDarkTheme()
}