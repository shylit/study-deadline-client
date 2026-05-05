package ru.mirea.shylit.studydeadline.core.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")

    data object Today : Screen("today")
    data object Week : Screen("week")
    data object Subjects : Screen("subjects")
    data object Search : Screen("search")
    data object Settings : Screen("settings")
}