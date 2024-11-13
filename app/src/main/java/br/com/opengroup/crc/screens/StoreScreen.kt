package br.com.opengroup.crc.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.screens.fragments.TopNavBarComponent

@Composable
fun StoreScreen(navController: NavController) {
    Column {
        TopNavBarComponent("Store", navController)
        Text("Store")
    }
}

@Preview
@Composable
fun StoreScreenPreview() {
    StoreScreen(rememberNavController())
}