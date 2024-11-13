package br.com.opengroup.crc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.screens.fragments.TopNavBarComponent
import br.com.opengroup.crc.ui.theme.Typography

var points = 1000

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopNavBarComponent("Dashboard", navController)
        Text("Seja bem-vindo ao CRC!", style = Typography.titleLarge)
        Text("Você possui $points pontos", style = Typography.titleMedium)

        Button(onClick = { navController.navigate("store") }) {
            Text("Gastar meus pontos")
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}