package ru.mirea.shylit.studydeadline.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mirea.shylit.studydeadline.presentation.auth.login.LoginScreen
import ru.mirea.shylit.studydeadline.presentation.auth.register.RegisterScreen
import ru.mirea.shylit.studydeadline.presentation.main.MainScreen
import ru.mirea.shylit.studydeadline.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onAuthorized = {
                    navController.navigate("main") {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                onUnauthorized = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("main") {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterClick = {
                    navController.navigate("main") {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("main") {

            MainScreen(

                onLogoutClick = {

                    navController.navigate(Screen.Login.route) {

                        popUpTo("main") {

                            inclusive = true

                        }

                    }

                }

            )

        }
    }
}