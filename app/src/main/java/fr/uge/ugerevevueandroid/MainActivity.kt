package fr.uge.ugerevevueandroid

import AdminPage
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.CodePage
import fr.uge.ugerevevueandroid.page.CreatePage
import fr.uge.ugerevevueandroid.page.HOME_COLOR
import fr.uge.ugerevevueandroid.page.HomePage
import fr.uge.ugerevevueandroid.page.LoginPage
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.page.PasswordPage
import fr.uge.ugerevevueandroid.page.ReviewPage
import fr.uge.ugerevevueandroid.page.SignupPage
import fr.uge.ugerevevueandroid.page.UserPage
import fr.uge.ugerevevueandroid.service.CameraCaller
import fr.uge.ugerevevueandroid.ui.theme.UGEReveVueAndroidTheme
import fr.uge.ugerevevueandroid.visual.Navbar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Initialisation du ViewModel
            val viewModel = viewModel<MainViewModel>()

            UGEReveVueAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Application(viewModel, this.application)
                }
            }
        }
        /* intent = Intent(this, CameraCaller::class.java)
            startActivity(intent)
            finish()*/
    }
}

@Composable
fun Application(viewModel : MainViewModel, application: Application){
    /*
    var needToReload by remember { mutableStateOf(true) }
    LaunchedEffect(viewModel.triggerReloadPage){
        println("normalement on reload")
        needToReload = false
    }
    */
    Column(Modifier.background(HOME_COLOR)) {
        Navbar(application = application, viewModel = viewModel)
        when(viewModel.currentPage){
            Page.HOME -> HomePage(application=application, viewModel = viewModel)
            Page.LOGIN -> LoginPage(application=application, viewModel = viewModel)
            Page.REVIEW -> ReviewPage(application=application, viewModel = viewModel)
            Page.SIGNUP -> SignupPage(application=application, viewModel = viewModel)
            Page.USER -> UserPage(application=application, viewModel = viewModel)
            Page.CODE -> CodePage(application= application, viewModel = viewModel)
            Page.CREATE -> CreatePage(viewModel = viewModel)
            Page.ADMIN -> AdminPage(application= application, viewModel = viewModel)
            Page.PASSWORD -> PasswordPage(application=application, viewModel=viewModel)
        }

    }
}


