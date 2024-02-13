package fr.uge.ugerevevueandroid.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CodePage(redirection : (Page) -> Unit){
    Text(text = "Page d'un code")
}