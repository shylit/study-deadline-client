package ru.mirea.shylit.studydeadline.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mirea.shylit.studydeadline.domain.repositories.ThemeRepository
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ThemeRepository {

    private val darkThemeKey = booleanPreferencesKey("dark_theme")

    override fun observeDarkTheme(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[darkThemeKey] ?: false
        }
    }

    override suspend fun setDarkTheme(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[darkThemeKey] = enabled
        }
    }
}