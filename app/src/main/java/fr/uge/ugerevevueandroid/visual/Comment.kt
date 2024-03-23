package fr.uge.ugerevevueandroid.visual

import TokenManager
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import fr.uge.ugerevevueandroid.information.CommentInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun commentDeleted(application: Application, commentId: Long) {
    return withContext(Dispatchers.IO) {
        var response = ApiService(application).adminPermitService()
            .commentDeleted(commentId)
            .execute()
        if (response.isSuccessful){
            response.body()
        }
    }
}

@Composable
fun Comment(viewModel: MainViewModel, application: Application, comment: CommentInformation){
    var deleteButtonClickedd by remember { mutableStateOf("NotDeleted") }
    LaunchedEffect(deleteButtonClickedd) {
        if (deleteButtonClickedd == "Deleted"){
            commentDeleted(application, comment.id)
        }
    }
    Surface(
        shadowElevation = 8.dp,
        border = BorderStroke(0.dp, Color.Gray),
        shape = RoundedCornerShape(16.dp),
        color = Color(230, 229, 229, 255),
        contentColor = Color.Black,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp),
        ) {
            Row {
                Text(
                    text = comment.userInformation.username,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /*TODO*/}
                )
                val auth = TokenManager(application).getAuth()
                if (auth != null && auth.role == "ADMIN"){
                    Button(
                        onClick = {
                            deleteButtonClickedd = "Deleted"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White, // Fond blanc
                            contentColor = Color.Black // Texte noir
                        ),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }

            Text(
                text = comment.userInformation.username,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    viewModel.changeCurrentPage(Page.USER)
                    viewModel.changeCurrentUserToDisplay(comment.userInformation.username)
                },
            )
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            Text(text = comment.content)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "${comment.date}", fontSize = 10.sp)
            }
        }
    }
}