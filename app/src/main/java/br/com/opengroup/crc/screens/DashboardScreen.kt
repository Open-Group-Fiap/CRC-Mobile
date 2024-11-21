package br.com.opengroup.crc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.api.FaturaApi
import br.com.opengroup.crc.api.MoradorApi
import br.com.opengroup.crc.api.RetrofitHelper
import br.com.opengroup.crc.cache.LocalDatabase
import br.com.opengroup.crc.firebase.logFirebaseApi
import br.com.opengroup.crc.screens.fragments.TopNavBarComponent
import br.com.opengroup.crc.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(navController: NavController) {
    val points = remember { mutableStateOf("...") }
    val id = remember { mutableStateOf("") }
    val reloadPoints = remember { mutableStateOf(false) }
    LaunchedEffect(reloadPoints.value) {
        CoroutineScope(Dispatchers.IO).launch {
            val userApi = RetrofitHelper.retrofit.create(MoradorApi::class.java)
            val email = LocalDatabase(navController.context).getCredentials().first.toString()
            try {
                val res = userApi.getMoradorByEmail(email)
                if (res.isSuccessful) {
                    points.value = res.body()?.pontos.toString()
                    id.value = res.body()?.id.toString()
                } else {
                    logFirebaseApi("Erro ao buscar pontos do usuário", email)
                }
            } catch (e: Exception) {
                logFirebaseApi("Erro ao buscar pontos do usuário ${e.message}", email)
            }

        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TopNavBarComponent("Dashboard", navController)
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {

            Text("Seja bem-vindo ao CRC!", style = Typography.titleLarge)
            Text("Você possui ${points.value} pontos", style = Typography.titleMedium)

            Button(onClick = { navController.navigate("store") }) {
                Text("Gastar meus pontos")
            }
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val faturaApi = RetrofitHelper.retrofit.create(FaturaApi::class.java)
                        try {
                            val response = faturaApi.randomFatura(id.value)
                            if (response.isSuccessful) {
                                logFirebaseApi("Fatura gerada com sucesso", id.value)
                                reloadPoints.value = !reloadPoints.value
                            } else {
                                logFirebaseApi("Erro ao gerar fatura", id.value)
                            }
                        } catch (e: Exception) {
                            logFirebaseApi("Erro ao gerar fatura ${e.message}", id.value)
                        }
                    }

                },
                enabled = points.value != "..."
            ) {
                Text("(DEBUG) Ganhe pontos")
            }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
