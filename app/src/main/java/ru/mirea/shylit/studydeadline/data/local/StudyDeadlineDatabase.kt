package ru.mirea.shylit.studydeadline.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mirea.shylit.studydeadline.data.local.dao.SearchHistoryDao
import ru.mirea.shylit.studydeadline.data.local.dao.SubjectDao
import ru.mirea.shylit.studydeadline.data.local.dao.TaskDao
import ru.mirea.shylit.studydeadline.data.local.entities.SearchHistoryEntity
import ru.mirea.shylit.studydeadline.data.local.entities.SubjectEntity
import ru.mirea.shylit.studydeadline.data.local.entities.TaskEntity

@Database(
    entities = [
        SubjectEntity::class,
        TaskEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class StudyDeadlineDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    abstract fun taskDao(): TaskDao

    abstract fun searchHistoryDao(): SearchHistoryDao
}