package ru.mirea.shylit.studydeadline.domain.usecases.auth

import ru.mirea.shylit.studydeadline.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.login(email, password)
}