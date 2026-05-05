package ru.mirea.shylit.studydeadline.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.mirea.shylit.studydeadline.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = runBlocking {
            authRepository.getIdToken().getOrNull()
        }

        val requestBuilder = originalRequest.newBuilder()

        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader(
                name = "Authorization",
                value = "Bearer $token"
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}