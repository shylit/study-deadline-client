package ru.mirea.shylit.studydeadline.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.mirea.shylit.studydeadline.data.local.StudyDeadlineDatabase
import ru.mirea.shylit.studydeadline.data.local.dao.SearchHistoryDao
import ru.mirea.shylit.studydeadline.data.local.dao.SubjectDao
import ru.mirea.shylit.studydeadline.data.local.dao.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StudyDeadlineDatabase {
        return Room.databaseBuilder(
            context,
            StudyDeadlineDatabase::class.java,
            "study_deadline.db"
        ).build()
    }

    @Provides
    fun provideSubjectDao(database: StudyDeadlineDatabase): SubjectDao {
        return database.subjectDao()
    }

    @Provides
    fun provideTaskDao(database: StudyDeadlineDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideSearchHistoryDao(database: StudyDeadlineDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }
}