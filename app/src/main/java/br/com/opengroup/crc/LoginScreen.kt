package br.com.opengroup.crc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.ui.theme.CRCTheme

@Composable
fun LoginScreen(navController: NavController) {
    Column(Modifier.padding(16.dp)) {
        Button(onClick = { navController.navigate("home") }) {
            Text("Ir para a primeira tela")
        }
        Text(text = "Esta é a segunda tela!")
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    CRCTheme {
        LoginScreen(rememberNavController())
    }
}