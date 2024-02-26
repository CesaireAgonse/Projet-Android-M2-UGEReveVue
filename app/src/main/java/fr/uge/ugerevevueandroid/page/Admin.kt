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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page

@Composable
fun AdminPage(viewModel : MainViewModel) {
    val users = mutableListOf<SimpleUserInformation>()
    var admin = SimpleUserInformation(1, "admin", null,true)
    var czer = SimpleUserInformation(2, "czer", HashSet<SimpleUserInformation>(),false)

    users.add(admin)
    users.add(czer)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {

        Row {
            Text(
                text = "Moderation Page",
                modifier = Modifier.weight(1f),
                fontSize = 30.sp
            )
        }


        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp))

        users.forEach{
            UserAdmin(viewModel, it)
        }


    }

}

@Composable
fun UserAdmin(viewModel : MainViewModel, user : SimpleUserInformation){

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${user.username}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    /*
                    viewModel.changeCurrentUserToDisplay(user)
                    viewModel.changeCurrentPage(Page.USER)
                    */
                })
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Delete, contentDescription = "DeleteUser")
            }
        }
        
        Text(text = "Liste de tout ses posts ...")
    }




}