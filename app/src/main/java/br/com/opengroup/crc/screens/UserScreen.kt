package br.com.opengroup.crc.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun UserScreen(navController: NavController) {
    Column {
        Text("User")
    }
}

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen(rememberNavController())
}
