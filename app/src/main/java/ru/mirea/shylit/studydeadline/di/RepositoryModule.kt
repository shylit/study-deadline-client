package ru.mirea.shylit.studydeadline.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mirea.shylit.studydeadline.data.repositories.SubjectRepositoryImpl
import ru.mirea.shylit.studydeadline.data.repositories.TaskRepositoryImpl
import ru.mirea.shylit.studydeadline.domain.repositories.SubjectRepository
import ru.mirea.shylit.studydeadline.domain.repositories.TaskRepository
import javax.inject.Singleton
import ru.mirea.shylit.studydeadline.data.repositories.SearchHistoryRepositoryImpl
import ru.mirea.shylit.studydeadline.domain.repositories.SearchHistoryRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindSearchHistoryRepository(
        impl: SearchHistoryRepositoryImpl
    ): SearchHistoryRepository
}