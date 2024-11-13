package br.com.opengroup.crc.screens.fragments


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import br.com.opengroup.crc.ui.theme.MainColor

@Composable
fun TopNavBarComponent(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MainColor)
    )
    {
        Text(
            title, style = TextStyle(
                color = androidx.compose.ui.graphics.Color.White
            )
        )
    }
}