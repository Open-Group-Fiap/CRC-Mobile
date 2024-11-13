package br.com.opengroup.crc.screens.fragments


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.opengroup.crc.R
import br.com.opengroup.crc.ui.theme.MainColor

@Composable
fun TopNavBarComponent(title: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MainColor)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    )
    {
        Text(
            title, style = TextStyle(
                fontSize = 36.sp,
                color = androidx.compose.ui.graphics.Color.White
            )
        )
        Image(
            painter = painterResource(id = R.drawable.baseline_person_24),
            contentDescription = "User",
            modifier = Modifier
                .clickable {
                    navController.navigate("user")
                }
                .height(42.dp)
                .width(42.dp)
        )
    }
}