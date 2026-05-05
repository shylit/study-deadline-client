package ru.mirea.shylit.studydeadline.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import android.app.Activity
import androidx.compose.ui.platform.LocalContext

@Composable
fun SettingsScreen(
    onLogoutClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Настройки",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(

            onClick = {

                viewModel.logout()

                onLogoutClick()

            },

            modifier = Modifier

                .fillMaxWidth()

                .padding(top = 24.dp)

        ) {

            Text("Выйти из аккаунта")

        }
    }
}