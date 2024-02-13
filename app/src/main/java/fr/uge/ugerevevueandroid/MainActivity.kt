package fr.uge.ugerevevueandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import fr.uge.ugerevevueandroid.page.CodePage
import fr.uge.ugerevevueandroid.page.CreatePage
import fr.uge.ugerevevueandroid.page.HomePage
import fr.uge.ugerevevueandroid.page.LoginPage
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.page.SignupPage
import fr.uge.ugerevevueandroid.page.UserPage
import fr.uge.ugerevevueandroid.ui.theme.UGEReveVueAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UGEReveVueAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Application()
                }
            }
        }
    }
}

@Composable
fun Application(){
    var page by remember { mutableStateOf(Page.HOME) }
    var userProfile by remember { mutableStateOf<SimpleUserInformation>(SimpleUserInformation()) }
    Column {
        Navbar(onClickButton = { page = it })
        when(page){
            Page.HOME -> HomePage( redirection = {page = it}, setUser = {userProfile = it})
            Page.LOGIN -> LoginPage()
            Page.SIGNUP -> SignupPage()
            Page.USER -> UserPage(userProfile)
            Page.CODE -> CodePage(redirection = {page = it})
            Page.CREATE -> CreatePage(redirection = {page = it})
        }
    }
}