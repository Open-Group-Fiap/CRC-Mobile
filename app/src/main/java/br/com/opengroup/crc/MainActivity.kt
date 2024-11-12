package br.com.opengroup.crc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.ui.theme.CRCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CRCTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(innerPadding)) {
                        composable("home") {
                            Greeting("Android", navController)
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, navController: NavController) {
    Column(Modifier.padding(16.dp)) {
        Button(onClick = { navController.navigate("login") }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Text("Ir para a segunda tela")
        }
        Text(
            text = "Olá $name!",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CRCTheme {
        Greeting("Android", rememberNavController())
    }
}