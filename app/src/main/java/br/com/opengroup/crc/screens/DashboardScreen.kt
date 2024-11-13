package br.com.opengroup.crc.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.screens.fragments.TopNavBarComponent

@Composable
fun DashboardScreen(navController: NavController) {
    Column {
        TopNavBarComponent("Dashboard", navController)
        Text("Dashboard")
        Button(onClick = { navController.navigate("store") }) {
            Text("Store")
        }
        Button(onClick = { navController.navigate("user") }) {
            Text("User")
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}