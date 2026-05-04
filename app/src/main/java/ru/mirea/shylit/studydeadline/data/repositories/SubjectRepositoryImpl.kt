package ru.mirea.shylit.studydeadline.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mirea.shylit.studydeadline.data.local.dao.SubjectDao
import ru.mirea.shylit.studydeadline.data.mappers.toDomain
import ru.mirea.shylit.studydeadline.data.mappers.toEntity
import ru.mirea.shylit.studydeadline.data.remote.api.SubjectApi
import ru.mirea.shylit.studydeadline.data.remote.dto.CreateSubjectRequest
import ru.mirea.shylit.studydeadline.data.remote.dto.UpdateSubjectRequest
import ru.mirea.shylit.studydeadline.domain.models.Subject
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectApi: SubjectApi,
    private val subjectDao: SubjectDao
) : SubjectRepository {

    override fun getSubjects(): Flow<List<Subject>> {
        return subjectDao.observeSubjects()
            .map { subjects -> subjects.map { it.toDomain() } }
    }

    override suspend fun refreshSubjects() {
        val remoteSubjects = subjectApi.getSubjects()
            .map { it.toDomain() }

        subjectDao.insertSubjects(remoteSubjects.map { it.toEntity() })
    }

    override suspend fun createSubject(
        name: String,
        color: String?
    ): Result<Subject> {
        return runCatching {
            val subject = subjectApi.createSubject(
                CreateSubjectRequest(
                    name = name,
                    color = color
                )
            ).toDomain()

            subjectDao.insertSubject(subject.toEntity())
            subject
        }
    }

    override suspend fun updateSubject(
        id: String,
        name: String,
        color: String?
    ): Result<Subject> {
        return runCatching {
            val subject = subjectApi.updateSubject(
                id = id,
                request = UpdateSubjectRequest(
                    name = name,
                    color = color
                )
            ).toDomain()

            subjectDao.insertSubject(subject.toEntity())
            subject
        }
    }

    override suspend fun deleteSubject(id: String): Result<Unit> {
        return runCatching {
            subjectApi.deleteSubject(id)
            subjectDao.deleteSubject(id)
        }
    }
}