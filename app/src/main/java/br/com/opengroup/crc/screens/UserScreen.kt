package br.com.opengroup.crc.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.api.MoradorApi
import br.com.opengroup.crc.api.RetrofitHelper
import br.com.opengroup.crc.cache.LocalDatabase
import br.com.opengroup.crc.firebase.logFirebaseApi
import br.com.opengroup.crc.models.Morador
import br.com.opengroup.crc.models.network.MoradorRequest
import br.com.opengroup.crc.ui.theme.LabelInput
import br.com.opengroup.crc.ui.theme.OppostColor
import br.com.opengroup.crc.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserScreen(navController: NavController) {
    val user: MutableState<Morador?> = remember { mutableStateOf(null) }
    val newEmail = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val newQtyResidents = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }
    if (user.value == null) {
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val userApi = RetrofitHelper.retrofit.create(MoradorApi::class.java)
                try {
                    val res =
                        userApi.getMoradorByEmail(LocalDatabase(navController.context).getCredentials().first.toString())
                    if (res.isSuccessful) {
                        user.value = res.body()
                        newEmail.value = user.value!!.auth.email
                        newQtyResidents.value = user.value!!.qtdMoradores.toString()
                    } else {
                        val error = res.errorBody()?.string()
                        logFirebaseApi(
                            "Erro ao buscar usuário ${error}",
                            LocalDatabase(navController.context).getCredentials().first.toString()
                        )
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                navController.context,
                                "Erro ao buscar usuário",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    logFirebaseApi(
                        "Erro ao buscar usuário ${e.message}",
                        LocalDatabase(navController.context).getCredentials().first.toString()
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dados de usuário", style = Typography.titleLarge)
        if (user.value != null) {
            // User form data
            OutlinedTextField(
                value = user.value!!.nome,
                enabled = false,
                onValueChange = { },
                label = { Text("Nome") },
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = newEmail.value,
                onValueChange = { newEmail.value = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = newPassword.value,
                onValueChange = { newPassword.value = it },
                label = {
                    Text(
                        "Senha (mantenha vazia para não mudar)",
                        style = Typography.bodySmall
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = user.value!!.cpf,
                enabled = false,
                onValueChange = {},
                label = { Text("CPF") },
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = newQtyResidents.value,
                onValueChange = { newQtyResidents.value = it },
                label = { Text("Quantidade de moradores") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = user.value!!.condominio.nome,
                enabled = false,
                onValueChange = {},
                label = { Text("Condomínio") },
                textStyle = LabelInput
            )
            Button(onClick = {
                if (newEmail.value.isEmpty() || newPassword.value.isEmpty() || newQtyResidents.value.isEmpty()) {
                    Toast.makeText(
                        navController.context,
                        "Preencha todos os campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                CoroutineScope(Dispatchers.IO).launch {
                    val userApi = RetrofitHelper.retrofit.create(MoradorApi::class.java)
                    val password =
                        if (newPassword.value.isEmpty()) LocalDatabase(navController.context).getCredentials().second!! else newPassword.value
                    try {
                        val res = userApi.updateMorador(
                            user.value!!.id,
                            MoradorRequest(
                                user.value!!.condominio.id,
                                newEmail.value,
                                password,
                                user.value!!.cpf,
                                user.value!!.nome,
                                newQtyResidents.value.toInt(),
                                user.value!!.identificadorRes
                            )
                        )
                        if (res.isSuccessful) {
                            logFirebaseApi(
                                "Usuário atualizado com sucesso",
                                LocalDatabase(navController.context).getCredentials().first.toString()
                            )
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    navController.context,
                                    "Usuário atualizado com sucesso",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            error.value = res.errorBody()?.string() ?: "Erro desconhecido"
                            logFirebaseApi(
                                "Erro ao atualizar usuário ${error.value}",
                                LocalDatabase(navController.context).getCredentials().first.toString()
                            )
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    navController.context,
                                    "Erro ao atualizar usuário",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        logFirebaseApi(
                            "Erro ao atualizar usuário ${e.message}",
                            LocalDatabase(navController.context).getCredentials().first.toString()
                        )
                    }
                }

            }) {
                Text("Atualizar")
            }
            Button(
                onClick = {
                    LocalDatabase(navController.context).clearCredentials()
                    navController.navigate("home") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Sair")
            }
            if (error.value.isNotEmpty()) {
                Text(error.value, style = Typography.bodySmall, color = Color.Red)
            }

        } else {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen(rememberNavController())
}
