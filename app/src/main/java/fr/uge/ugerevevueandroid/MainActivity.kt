package fr.uge.ugerevevueandroid

import AdminPage
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
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
import fr.uge.ugerevevueandroid.page.HOME_COLOR
import fr.uge.ugerevevueandroid.page.HomePage
import fr.uge.ugerevevueandroid.page.LoginPage
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.page.PasswordPage
import fr.uge.ugerevevueandroid.page.ReviewPage
import fr.uge.ugerevevueandroid.page.SignupPage
import fr.uge.ugerevevueandroid.page.UserPage
import fr.uge.ugerevevueandroid.activity.CameraCaller
import fr.uge.ugerevevueandroid.service.NotificationService
import fr.uge.ugerevevueandroid.ui.theme.UGEReveVueAndroidTheme
import fr.uge.ugerevevueandroid.visual.Navbar


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.activity = this
            UGEReveVueAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Application(viewModel, this.application)
                }
            }
        }
        viewModel.showDynamicNotification("Bienvenue sur UGEReveVue", "Je te présente notre application mobile.", this)
    }

//    private fun showNotificationEnableDialog() {
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val notificationsEnabled = sharedPreferences.getBoolean("notificationsEnabled", false)
//
//        if (!notificationsEnabled) {
//            AlertDialog.Builder(this)
//                .setTitle("Enable Notifications")
//                .setMessage("Would you like to enable notifications?")
//                .setPositiveButton("Yes") { dialog, _ ->
//                    sharedPreferences.edit().putBoolean("notificationsEnabled", true).apply()
//                    dialog.dismiss()
//                }
//                .setNegativeButton("No") { dialog, _ ->
//                    sharedPreferences.edit().putBoolean("notificationsEnabled", false).apply()
//                    dialog.dismiss()
//                }
//                .setCancelable(false)
//                .show()
//        }
//    }
}



@Composable
fun Application(viewModel : MainViewModel, application: Application){
    Column(Modifier.background(HOME_COLOR)) {
        Navbar(application = application, viewModel = viewModel)
        when(viewModel.currentPage){
            Page.HOME -> HomePage(application=application, viewModel = viewModel)
            Page.LOGIN -> LoginPage(application=application, viewModel = viewModel)
            Page.REVIEW -> ReviewPage(application=application, viewModel = viewModel)
            Page.SIGNUP -> SignupPage(application=application, viewModel = viewModel)
            Page.USER -> UserPage(application=application, viewModel = viewModel)
            Page.CODE -> CodePage(application= application, viewModel = viewModel)
            Page.CREATE -> CreatePage(application= application, viewModel = viewModel)
            Page.ADMIN -> AdminPage(application= application, viewModel = viewModel)
            Page.PASSWORD -> PasswordPage(application=application, viewModel=viewModel)
        }

    }

}



