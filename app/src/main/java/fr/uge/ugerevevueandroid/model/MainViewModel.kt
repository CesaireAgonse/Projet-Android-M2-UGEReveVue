package fr.uge.ugerevevueandroid.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import fr.uge.ugerevevueandroid.page.Page

class MainViewModel : ViewModel() {
    var currentPage by mutableStateOf(Page.HOME)
        private set

    private var currentUserLogged by mutableStateOf<SimpleUserInformation>(SimpleUserInformation())
    var currentUserToDisplay by mutableStateOf<SimpleUserInformation>(SimpleUserInformation())
        private set
    var currentCodeToDisplay by mutableStateOf<CodeInformation>(CodeInformation())
        private set

    fun changeCurrentPage(page : Page){currentPage = page }

    fun changeCurrentUserToDisplay(user : SimpleUserInformation){ currentUserToDisplay = user }

    fun changeCurrentCodeToDisplay(code : CodeInformation){ currentCodeToDisplay = code }

    fun adminAccess() : Boolean {
        if (currentUserLogged != null && currentUserLogged.isAdmin){
            return true
        }
        return false
    }
}