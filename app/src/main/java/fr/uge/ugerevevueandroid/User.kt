package fr.uge.ugerevevueandroid

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.uge.ugerevevueandroid.information.SimpleUserInformation

@Composable
fun User(user : SimpleUserInformation) {
    Text(text = "Page de l'user ${user.username}")
}