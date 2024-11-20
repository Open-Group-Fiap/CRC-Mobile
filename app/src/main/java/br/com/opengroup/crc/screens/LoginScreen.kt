package br.com.opengroup.crc.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.api.MoradorApi
import br.com.opengroup.crc.api.RetrofitHelper
import br.com.opengroup.crc.cache.LocalDatabase
import br.com.opengroup.crc.firebase.logFirebaseApi
import br.com.opengroup.crc.models.network.LoginRequest
import br.com.opengroup.crc.ui.theme.CRCTheme
import br.com.opengroup.crc.ui.theme.LabelInput
import br.com.opengroup.crc.ui.theme.MainColor
import br.com.opengroup.crc.ui.theme.OppostColor
import br.com.opengroup.crc.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Login", style = Typography.titleLarge)
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email", style = LabelInput) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Senha", style = LabelInput) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = LabelInput
            )
            Button(
                onClick = {
                    if (email.value.isEmpty() || password.value.isEmpty()) {
                        Toast.makeText(
                            navController.context,
                            "Preencha todos os campos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        val loginApi = RetrofitHelper.retrofit.create(MoradorApi::class.java)
                        try {
                            val res = loginApi.login(LoginRequest(email.value, password.value))
                            if (res.isSuccessful) {
                                logFirebaseApi("Login realizado com sucesso", email.value)
                                LocalDatabase(navController.context).saveCredentials(
                                    email.value,
                                    password.value
                                )
                                withContext(Dispatchers.Main) {
                                    navController.navigate("dashboard") {
                                        popUpTo("home") {
                                            inclusive = true
                                        }
                                    }
                                }
                            } else {
                                val error = res.errorBody()?.string()
                                logFirebaseApi(
                                    "Erro ao realizar login, ${
                                        (error) ?: "Erro desconhecido"
                                    }", email.value
                                )
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        navController.context,
                                        "Erro: ${error ?: "Erro desconhecido"}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            logFirebaseApi("Erro ao realizar login ${e.message}", email.value)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    navController.context,
                                    "Erro: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)
            ) {
                Text("Login")
            }
        }
        Button(
            onClick = {
                navController.navigate("register") {
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = OppostColor)
        ) {
            Text("Registrar-se")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CRCTheme {
        LoginScreen(navController = rememberNavController())
    }
}