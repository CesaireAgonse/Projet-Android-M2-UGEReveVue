package fr.uge.ugerevevueandroid.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.service.NotificationService

class MainViewModel : ViewModel() {
    var currentPage by mutableStateOf(Page.HOME)
        private set
    var triggerReloadPage by mutableStateOf(false)
        private set

    var currentUserLogged by mutableStateOf<UserInformation>(UserInformation("", "",   0, 0,0, 0,false))
        private set
    var currentUserToDisplay by mutableStateOf<String>("")

    var activity:Activity? by mutableStateOf(null)

    var photoURL:String? by mutableStateOf(null)

    var currentCodeToDisplay by mutableLongStateOf(0)
        private set

    var currentQuery by mutableStateOf("")
        private set

    var currentSortBy by mutableStateOf("")
        private set

    fun changeCurrentPage(page : Page){ currentPage = page }

    fun reloadPage(){ triggerReloadPage = !triggerReloadPage }

    fun changeCurrentUserToDisplay(username : String){ currentUserToDisplay = username }

    fun changeCurrentUserLogged(user : UserInformation){ currentUserLogged = user }

    fun changeCurrentCodeToDisplay(codeId : Long){ currentCodeToDisplay = codeId }

    fun changeCurrentQuery(query : String){ currentQuery = query }

    fun changeCurrentSortBy(sortBy : String){ currentSortBy = sortBy }


    fun showDynamicNotification(title: String, content: String, context: Context) {
        val serviceIntent = Intent(context, NotificationService::class.java)
        serviceIntent.putExtra("title", title)
        serviceIntent.putExtra("content", content)
        context.startService(serviceIntent)
    }

}