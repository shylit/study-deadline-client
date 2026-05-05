package ru.mirea.shylit.studydeadline.domain.usecases.auth

import ru.mirea.shylit.studydeadline.domain.repositories.AuthRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return repository.isUserAuthorized()
    }
}