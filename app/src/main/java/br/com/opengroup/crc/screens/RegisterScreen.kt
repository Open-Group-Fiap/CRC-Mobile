package br.com.opengroup.crc.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.opengroup.crc.ui.theme.CRCTheme
import br.com.opengroup.crc.ui.theme.LabelInput
import br.com.opengroup.crc.ui.theme.MainColor
import br.com.opengroup.crc.ui.theme.OppostColor
import br.com.opengroup.crc.ui.theme.Typography

@Composable
fun RegisterScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    val condoCode = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val residence = remember { mutableStateOf("") }
    val numPeople = remember { mutableStateOf("") }

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
                textStyle = LabelInput

            )
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Confirme a senha", style = LabelInput) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = cpf.value,
                onValueChange = { cpf.value = it },
                label = { Text("Cpf", style = LabelInput) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LabelInput
            )
            OutlinedTextField(
                value = condoCode.value,
                onValueChange = { condoCode.value = it },
                label = { Text("Código do condomínio", style = LabelInput) },
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
                onClick = {/*TODO*/
                    navController.navigate("dashboard") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MainColor)
            )
            {
                Text("Cadastre-se")
            }
        }
        Button(
            onClick = { navController.navigate("login") },
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