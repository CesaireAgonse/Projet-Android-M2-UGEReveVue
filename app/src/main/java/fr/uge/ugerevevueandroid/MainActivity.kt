package fr.uge.ugerevevueandroid

import AdminPage
import android.R
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
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
            // Initialisation du ViewModel
            var viewModel = viewModel<MainViewModel>()

            UGEReveVueAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Application(viewModel)
                }
            }
        }
    }
}

@Composable
fun Application(viewModel : MainViewModel){
    Column {
        Navbar(onClickButton = { viewModel.changeCurrentPage(it)})
        when(viewModel.currentPage){
            Page.HOME -> HomePage(viewModel = viewModel)
            Page.LOGIN -> LoginPage()
            Page.SIGNUP -> SignupPage()
            Page.USER -> UserPage(viewModel = viewModel)
            Page.CODE -> CodePage(viewModel = viewModel)
            Page.CREATE -> CreatePage(viewModel = viewModel)
            Page.ADMIN -> AdminPage(viewModel = viewModel)
        }
    }
}


