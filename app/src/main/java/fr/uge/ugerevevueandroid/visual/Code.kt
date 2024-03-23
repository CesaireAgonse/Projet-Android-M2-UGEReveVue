package fr.uge.ugerevevueandroid.visual

import TokenManager
import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.page.postCommented
import fr.uge.ugerevevueandroid.service.ApiService
import fr.uge.ugerevevueandroid.service.allPermitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun postVoted(application: Application, postId: Long, voteType: String):Long? {
    return withContext(Dispatchers.IO) {
        var response = ApiService(application).authenticateService()
            .postVoted(postId, voteType)
            .execute()
        response.body()

    }
}



@Composable
fun Code(application: Application, codeInformation : CodeInformation,viewModel: MainViewModel){
    var code:CodeInformation by remember { mutableStateOf( codeInformation)}
    var voteButtonClicked by remember { mutableStateOf("NotVoted") }
    var score by remember { mutableLongStateOf(code.score) }
    LaunchedEffect(voteButtonClicked) {
        if (voteButtonClicked != "NotVoted" && TokenManager(application).getAuth() != null){
            var temp = postVoted(application, code.id, voteButtonClicked)
            if (temp != null){
                score = temp
            }
        }
    }

    Surface(
        shadowElevation = 8.dp,
        border = BorderStroke(0.dp, Color.Gray),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        contentColor = Color.Black,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(Modifier.padding(5.dp)) {
            Text(
                text = code.title,
                fontSize = 30.sp,
            )
            Row {
                Text(
                    text = "from : ${code.userInformation.username}",
                    modifier = Modifier.clickable { viewModel.changeCurrentPage(Page.USER)
                        viewModel.changeCurrentUserToDisplay(code.userInformation.username)
                    },
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${code.date}", fontSize = 15.sp)
            }
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))

            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (code.voteType == "UpVote"){
                        Button(
                            onClick = {
                                voteButtonClicked = "UpVote"
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black, // Fond blanc
                                contentColor = Color.White // Texte noir
                            ),
                            shape = CircleShape,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "UpVote")
                        }
                    }
                    else{
                        Button(
                            onClick = { voteButtonClicked = "UpVote"
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // Fond blanc
                                contentColor = Color.Black // Texte noir
                            ),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "UpVote")
                        }
                    }

                    Text(text = "${score}")
                    if (code.voteType == "DownVote") {
                        Button(
                            onClick = { voteButtonClicked = "DownVote"
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black, // Fond blanc
                                contentColor = Color.White // Texte noir
                            ),
                            shape = CircleShape,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "DownVote")
                        }
                    }
                    else {
                        Button(
                            onClick = { voteButtonClicked = "DownVote"
                             },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White, // Fond blanc
                                contentColor = Color.Black // Texte noir
                            ),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "UpVote")
                        }
                    }
                }
                Column {
                    Text(text = code.description, textAlign = TextAlign.Justify)
                    Text(text = code.javaContent)
                    if (code.unitContent != null){
                        Text(text = code.unitContent)
                    }
                }
            }
        }
    }

}