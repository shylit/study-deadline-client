package ru.mirea.shylit.studydeadline.data.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import ru.mirea.shylit.studydeadline.domain.repositories.AuthRepository
import javax.inject.Inject
import ru.mirea.shylit.studydeadline.data.local.dao.SubjectDao
import ru.mirea.shylit.studydeadline.data.local.dao.TaskDao
import ru.mirea.shylit.studydeadline.data.local.dao.SearchHistoryDao

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val subjectDao: SubjectDao,
    private val taskDao: TaskDao,
    private val searchHistoryDao: SearchHistoryDao
) : AuthRepository {

    override fun isUserAuthorized(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return runCatching {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
        }.map { Unit }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {
        return runCatching {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
        }.map { Unit }
    }

    override suspend fun getIdToken(): Result<String> {
        return runCatching {
            val user = firebaseAuth.currentUser
                ?: error("Пользователь не авторизован")

            user.getIdToken(true)
                .await()
                .token
                ?: error("Не удалось получить Firebase token")
        }
    }

    override suspend fun logout() {
        subjectDao.clearSubjects()
        taskDao.clearTasks()
        searchHistoryDao.clearHistory()
        firebaseAuth.signOut()
    }
}