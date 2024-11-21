package br.com.opengroup.crc.screens

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
import androidx.compose.ui.graphics.Color
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
import br.com.opengroup.crc.models.network.MoradorRequest
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
fun RegisterScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val residence = remember { mutableStateOf("") }
    val numPeople = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }
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
            Text("Cadastro", style = Typography.titleLarge)

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
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Confirme a senha", style = LabelInput) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = cpf.value,
                onValueChange = { cpf.value = it },
                label = { Text("Cpf (só os digitos)", style = LabelInput) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Nome", style = LabelInput) },
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = residence.value,
                onValueChange = { residence.value = it },
                label = { Text("Complemento", style = LabelInput) },
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = numPeople.value,
                onValueChange = { numPeople.value = it },
                label = { Text("Quantidade de moradores", style = LabelInput) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LabelInput
            )
            Button(
                onClick = {
                    if (email.value.isEmpty() ||
                        password.value.isEmpty() ||
                        confirmPassword.value.isEmpty() ||
                        cpf.value.isEmpty() ||
                        name.value.isEmpty() ||
                        residence.value.isEmpty() ||
                        numPeople.value.isEmpty()
                    ) {
                        Toast.makeText(
                            navController.context,
                            "Preencha todos os campos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (password.value != confirmPassword.value) {
                        Toast.makeText(
                            navController.context,
                            "As senhas não coincidem",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (cpf.value.length != 11) {
                        Toast.makeText(
                            navController.context,
                            "CPF inválido",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        val registerApi = RetrofitHelper.retrofit.create(MoradorApi::class.java)
                        val firstThree = cpf.value.substring(0, 3)
                        val secondThree = cpf.value.substring(3, 6)
                        val thirdThree = cpf.value.substring(6, 9)
                        val lastTwo = cpf.value.substring(9, 11)
                        val formattedCpf = "$firstThree.$secondThree.$thirdThree-$lastTwo"
                        try {
                            val request = registerApi.register(
                                MoradorRequest(
                                    1, // Por enquanto, o id do condomínio é fixo
                                    email.value,
                                    password.value,
                                    formattedCpf,
                                    name.value,
                                    numPeople.value.toInt(),
                                    residence.value,
                                )
                            )
                            if (request.isSuccessful) {
                                logFirebaseApi("Cadastro realizado com sucesso", email.value)
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
                                error.value = request.errorBody()?.string() ?: "Erro desconhecido"
                                logFirebaseApi(
                                    "Erro ao realizar cadastro $error",
                                    email.value
                                )

                            }

                        } catch (e: Exception) {
                            logFirebaseApi("Erro ao realizar cadastro ${e.message}", email.value)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    navController.context,
                                    "Erro: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)
            )
            {
                Text("Cadastre-se")
            }
            if (error.value.isNotEmpty()) {
                Text(
                    text = error.value,
                    style = Typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = OppostColor)
        )
        {
            Text("Fazer Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    CRCTheme {
        RegisterScreen(navController = rememberNavController())
    }
}