package br.com.opengroup.crc.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.api.BonusApi
import br.com.opengroup.crc.api.MoradorApi
import br.com.opengroup.crc.api.RetrofitHelper
import br.com.opengroup.crc.cache.LocalDatabase
import br.com.opengroup.crc.firebase.logFirebaseApi
import br.com.opengroup.crc.models.Bonus
import br.com.opengroup.crc.models.network.MoradorBonusRequest
import br.com.opengroup.crc.screens.fragments.TopNavBarComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun StoreScreen(navController: NavController) {
    val bonusArray: MutableState<List<Bonus>?> = remember { mutableStateOf(null) }
    val reloadBonus = remember { mutableStateOf(false) }
    val points = remember { mutableStateOf("...") }
    val id = remember { mutableStateOf("") }
    LaunchedEffect(reloadBonus.value) {
        CoroutineScope(Dispatchers.IO).launch {
            val userApi = RetrofitHelper.retrofit.create(MoradorApi::class.java)
            val bonusApi = RetrofitHelper.retrofit.create(BonusApi::class.java)
            try {
                val response =
                    userApi.getMoradorByEmail(LocalDatabase(navController.context).getCredentials().first.toString())
                if (response.isSuccessful) {
                    points.value = response.body()?.pontos.toString()
                    id.value = response.body()?.id.toString()
                    val bonusResponse =
                        bonusApi.getBonusByCondominio(response.body()?.condominio?.id.toString())
                    if (bonusResponse.isSuccessful) {
                        bonusArray.value = bonusResponse.body()
                    } else {
                        logFirebaseApi(
                            "Erro ao buscar bônus do usuário",
                            LocalDatabase(navController.context).getCredentials().first.toString()
                        )
                    }
                } else {
                    logFirebaseApi(
                        "Erro ao buscar bônus do usuário",
                        LocalDatabase(navController.context).getCredentials().first.toString()
                    )
                }
            } catch (e: Exception) {
                logFirebaseApi(
                    "Erro ao buscar bônus do usuário ${e.message}",
                    LocalDatabase(navController.context).getCredentials().first.toString()
                )
            }

        }
    }
    Column {
        TopNavBarComponent("Loja", navController)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = br.com.opengroup.crc.R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = {
                        navController.popBackStack()
                    })
            )
            Text(
                "Pontos disponíveis: ${points.value}",
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            bonusArray.value?.let { bonuses ->
                items(bonuses) { bonus ->
                    Column {
                        Text(
                            bonus.nome, style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(bonus.descricao, style = TextStyle(fontSize = 16.sp))
                        Text(
                            "Custo: ${bonus.custo} pontos",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(
                            "Quantidade disponivel: ${bonus.qtd}",
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val response =
                                            RetrofitHelper.retrofit.create(BonusApi::class.java)
                                                .comprarBonus(
                                                    MoradorBonusRequest(
                                                        bonus.id,
                                                        id.value.toInt(),
                                                        1
                                                    )
                                                )
                                        if (response.isSuccessful) {
                                            reloadBonus.value = !reloadBonus.value
                                        } else {
                                            val error = response.errorBody()?.string()
                                            logFirebaseApi(
                                                "Erro ao resgatar bônus ${
                                                    error ?: response.message()
                                                }",
                                                LocalDatabase(navController.context).getCredentials().first.toString()
                                            )
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(
                                                    navController.context,
                                                    "Erro: ${error ?: response.message()}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        logFirebaseApi(
                                            "Erro ao resgatar bônus ${e.message}",
                                            LocalDatabase(navController.context).getCredentials().first.toString()
                                        )
                                    }
                                }
                            },
                            enabled = bonus.qtd > 0

                        ) {
                            Text("Resgatar")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StoreScreenPreview() {
    StoreScreen(rememberNavController())
}