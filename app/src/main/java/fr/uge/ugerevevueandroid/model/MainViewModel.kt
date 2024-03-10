package fr.uge.ugerevevueandroid.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.page.Page

class MainViewModel : ViewModel() {
    var currentPage by mutableStateOf(Page.HOME)
        private set

    private var currentUserLogged by mutableStateOf<UserInformation>(UserInformation(0, "", null,false))
    var currentUserToDisplay by mutableStateOf<String>("")

    var currentCodeToDisplay by mutableLongStateOf(0)
        private set

    fun changeCurrentPage(page : Page){currentPage = page }

    fun changeCurrentUserToDisplay(username : String){ currentUserToDisplay = username }

    fun changeCurrentCodeToDisplay(codeId : Long){ currentCodeToDisplay = codeId }

    fun adminAccess() : Boolean {
        if (currentUserLogged != null && currentUserLogged.isAdmin){
            return true
        }
        return false
    }
}