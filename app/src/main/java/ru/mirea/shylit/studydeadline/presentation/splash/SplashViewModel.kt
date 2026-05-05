package ru.mirea.shylit.studydeadline.presentation.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.mirea.shylit.studydeadline.domain.usecases.auth.CheckAuthUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    fun isAuthorized(): Boolean {
        return checkAuthUseCase()
    }
}