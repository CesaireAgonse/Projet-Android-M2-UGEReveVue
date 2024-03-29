import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.information.UserPageInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.service.getAllUsers
import fr.uge.ugerevevueandroid.service.userDeleted

@Composable
fun AdminPage(application: Application, viewModel : MainViewModel) {
    var userPageInformation: UserPageInformation? by remember {mutableStateOf( null)}
    var numberOfUser by remember {mutableStateOf(0) }
    val scrollState = rememberScrollState()

    LaunchedEffect(userPageInformation, viewModel.triggerReloadPage){
        userPageInformation = getAllUsers(application)
        if (userPageInformation != null && userPageInformation!!.resultNumber != null) {
            numberOfUser = userPageInformation!!.resultNumber
        }
    }
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Moderation Page",
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text =   "$numberOfUser users",
                fontSize = 15.sp
            )
        }
        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp))

        userPageInformation?.users?.forEach{
            UserAdmin(application, viewModel, it)
        }
    }
}

@Composable
fun UserAdmin(application: Application, viewModel : MainViewModel, user : UserInformation, modifier: Modifier = Modifier){
    var deleteButtonClicked by remember { mutableStateOf("NotDeleted") }
    LaunchedEffect(deleteButtonClicked) {
        if (deleteButtonClicked == "Deleted"){
            userDeleted(application, user.username)
            viewModel.reloadPage()
        }
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${user.username}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    viewModel.changeCurrentUserToDisplay(user.username)
                    viewModel.reloadPage()
                    viewModel.changeCurrentPage(Page.USER)
                })
            Spacer(modifier = Modifier.weight(1f))
            if (TokenManager(application).getAuth()?.role.equals("ADMIN")){
                Button(onClick = { deleteButtonClicked = "Deleted" }) {
                    Icon(Icons.Filled.Delete, contentDescription = "DeleteUser")
                }
            }
        }
    }
}