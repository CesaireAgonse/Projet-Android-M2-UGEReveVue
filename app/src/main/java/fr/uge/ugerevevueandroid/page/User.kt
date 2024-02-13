package fr.uge.ugerevevueandroid.page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fr.uge.ugerevevueandroid.information.SimpleUserInformation

@Composable
fun UserPage(user : SimpleUserInformation) {
    Text(text = "Page de l'user ${user.username}")
}