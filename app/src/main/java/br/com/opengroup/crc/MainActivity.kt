package br.com.opengroup.crc

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.api.BootApi
import br.com.opengroup.crc.api.RetrofitHelper
import br.com.opengroup.crc.cache.LocalDatabase
import br.com.opengroup.crc.firebase.logFirebaseApi
import br.com.opengroup.crc.screens.DashboardScreen
import br.com.opengroup.crc.screens.LoginScreen
import br.com.opengroup.crc.screens.RegisterScreen
import br.com.opengroup.crc.screens.StoreScreen
import br.com.opengroup.crc.screens.UserScreen
import br.com.opengroup.crc.ui.theme.ButtonLabel
import br.com.opengroup.crc.ui.theme.CRCTheme
import br.com.opengroup.crc.ui.theme.LabelLink
import br.com.opengroup.crc.ui.theme.MainColor
import br.com.opengroup.crc.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch{
            val bootApi = RetrofitHelper.retrofit.create(BootApi::class.java)
            val email = LocalDatabase(this@MainActivity).getEmail() ?: "Usuário não logado"
            try{
                val res = bootApi.boot()
                if(res.isSuccessful){
                    logFirebaseApi("Servidor iniciado com sucesso", email)
                }
                else{
                    logFirebaseApi("Erro ao iniciar servidor", email)
                }
            }
            catch (e: Exception){
                logFirebaseApi("Erro ao iniciar servidor ${e.message}", email)
            }
        }

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
                        composable("store") {
                            StoreScreen(navController)
                        }
                        composable("user") {
                            UserScreen(navController)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(navController: NavController) {
    
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CRC - Condomínios Reduzindo Consumo",
            style = Typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Seja bem vindo!",
            style = Typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text("Ir para o login", style = ButtonLabel)
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Ainda não tem conta?",
                style = Typography.labelSmall
            )
            ClickableText(
                text = AnnotatedString("Crie uma aqui!"),
                style = LabelLink,
                modifier = Modifier.padding(start = 4.dp)
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
