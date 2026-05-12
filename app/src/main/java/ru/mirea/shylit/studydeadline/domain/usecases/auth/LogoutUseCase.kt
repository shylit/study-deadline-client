package ru.mirea.shylit.studydeadline.domain.usecases.auth

import ru.mirea.shylit.studydeadline.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}