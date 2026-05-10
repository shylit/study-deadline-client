package ru.mirea.shylit.studydeadline.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.mirea.shylit.studydeadline.core.navigation.Screen
import ru.mirea.shylit.studydeadline.presentation.search.SearchScreen
import ru.mirea.shylit.studydeadline.presentation.settings.SettingsScreen
import ru.mirea.shylit.studydeadline.presentation.subjects.SubjectsScreen
import ru.mirea.shylit.studydeadline.presentation.tasks.SubjectTasksScreen
import ru.mirea.shylit.studydeadline.presentation.today.TodayScreen
import ru.mirea.shylit.studydeadline.presentation.week.WeekScreen
import ru.mirea.shylit.studydeadline.presentation.tasks.create.CreateTaskScreen
import ru.mirea.shylit.studydeadline.presentation.tasks.edit.EditTaskScreen

@Composable
fun MainScreen(
    onLogoutClick: () -> Unit
) {
    val navController = rememberNavController()

    val bottomItems = listOf(
        Screen.Today,
        Screen.Week,
        Screen.Subjects,
        Screen.Search,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                bottomItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                when (screen) {
                                    Screen.Today -> "Сегодня"
                                    Screen.Week -> "Неделя"
                                    Screen.Subjects -> "Предметы"
                                    Screen.Search -> "Поиск"
                                    Screen.Settings -> "Настройки"
                                    else -> ""
                                }
                            )
                        },
                        icon = {
                            Text(
                                when (screen) {
                                    Screen.Today -> "Д"
                                    Screen.Week -> "Н"
                                    Screen.Subjects -> "П"
                                    Screen.Search -> "?"
                                    Screen.Settings -> "⚙"
                                    else -> ""
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Today.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("create_task") {
                CreateTaskScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onTaskCreated = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Today.route) {
                TodayScreen(
                    onCreateTaskClick = {
                        navController.navigate("create_task")
                    },
                    onEditTaskClick = { taskId ->
                        navController.navigate("edit_task/$taskId")
                    }
                )
            }

            composable(Screen.Week.route) {
                WeekScreen()
            }

            composable(Screen.Subjects.route) {
                SubjectsScreen(
                    onSubjectClick = { subjectName ->
                        navController.navigate("subject_tasks/$subjectName")
                    }
                )
            }

            composable("subject_tasks/{subjectName}") { backStackEntry ->
                val subjectName = backStackEntry.arguments
                    ?.getString("subjectName")
                    .orEmpty()

                SubjectTasksScreen(
                    subjectName = subjectName,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Search.route) {
                SearchScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onLogoutClick = onLogoutClick
                )
            }

            composable("edit_task/{taskId}") { backStackEntry ->
                val taskId = backStackEntry.arguments
                    ?.getString("taskId")
                    .orEmpty()

                EditTaskScreen(
                    taskId = taskId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onTaskUpdated = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}