package br.com.opengroup.crc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.screens.DashboardScreen
import br.com.opengroup.crc.screens.LoginScreen
import br.com.opengroup.crc.screens.RegisterScreen
import br.com.opengroup.crc.ui.theme.CRCTheme
import br.com.opengroup.crc.ui.theme.LabelLink
import br.com.opengroup.crc.ui.theme.MainColor
import br.com.opengroup.crc.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CRCTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            Greeting(navController)
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("register") {
                            RegisterScreen(navController)
                        }
                        composable("dashboard") {
                            DashboardScreen(navController)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(navController: NavController) {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = "CRC - Condomínios Reduzindo Consumo",
            style = Typography.titleLarge
        )
        Text(
            text = "Seja bem vindo!",
            style = Typography.bodyLarge
        )
        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(containerColor = MainColor)
        ) {
            Text("Ir para o login")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Ainda não tem conta?",
                style = Typography.labelSmall
            )
            ClickableText(
                text = AnnotatedString("Crie uma aqui!"),
                style = LabelLink

            ) {
                navController.navigate("register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CRCTheme {
        Greeting(rememberNavController())
    }
}
