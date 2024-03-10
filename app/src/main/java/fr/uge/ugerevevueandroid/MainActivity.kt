package fr.uge.ugerevevueandroid

import AdminPage
import TokenManager
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.CodePage
import fr.uge.ugerevevueandroid.page.CreatePage
import fr.uge.ugerevevueandroid.page.HomePage
import fr.uge.ugerevevueandroid.page.LoginPage
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.page.PasswordPage
import fr.uge.ugerevevueandroid.page.ReviewPage
import fr.uge.ugerevevueandroid.page.SignupPage
import fr.uge.ugerevevueandroid.page.UserPage
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
    }
}

@Composable
fun Application(viewModel : MainViewModel, application: Application){
    Column {
        Navbar(application = application, viewModel = viewModel)
        when(viewModel.currentPage){
            Page.HOME -> HomePage(viewModel = viewModel)
            Page.LOGIN -> LoginPage(application=application, viewModel = viewModel)
            Page.REVIEW -> ReviewPage(application=application, viewModel = viewModel)
            Page.SIGNUP -> SignupPage(application=application, viewModel = viewModel)
            Page.USER -> UserPage(application=application, viewModel = viewModel)
            Page.CODE -> CodePage(application= application, viewModel = viewModel)
            Page.CREATE -> CreatePage(viewModel = viewModel)
            Page.ADMIN -> AdminPage(viewModel = viewModel)
            Page.PASSWORD -> PasswordPage(application=application, viewModel=viewModel)
        }
    }
}


