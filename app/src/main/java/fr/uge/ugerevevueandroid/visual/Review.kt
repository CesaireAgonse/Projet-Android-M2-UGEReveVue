package fr.uge.ugerevevueandroid.visual

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page

@Composable
fun Review(application: Application, review: ReviewInformation, modifier: Modifier = Modifier,viewModel: MainViewModel){
    var voteButtonClicked by remember { mutableStateOf("NotVoted") }
    var score by remember { mutableLongStateOf(review.score) }
    LaunchedEffect(voteButtonClicked) {
        if (voteButtonClicked != "NotVoted"){
            score = postVoted(application, review.id, voteButtonClicked)
        }
    }
    Column (
        modifier = modifier.padding(2.dp)
        // mettre un petit background et delimiter chaque component
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(text = "reviews: ${review.reviews}",
                fontSize = 10.sp)
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(text = "comments: ${review.comments}",
                fontSize = 10.sp)
        }
        Row {
            Text(
                text = "from : ${review.userInformation.username}",
                modifier = Modifier.clickable {
                    viewModel.changeCurrentPage(Page.USER)
                    viewModel.changeCurrentUserToDisplay(review.userInformation.username)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${review.date}")
        }
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { voteButtonClicked = "UpVote" },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color),
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = colorResource(id = R.color.button_color_2)
                    ),
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "UpVote")
                }
                Text(text = "$score")
                Button(onClick = { voteButtonClicked = "DownVote" },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color),
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = colorResource(id = R.color.button_color_2)
                    ),
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "DownVote")
                }
            }
            Column {
                Row {
                    Text(
                        text = review.title,
                        fontSize = 20.sp
                    )
                }
                Row {
                    for (content in review.content){
                        if (content.codeSelection != "" && content.codeSelection != null){
                            Text(
                                text = content.codeSelection,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Justify,
                                lineHeight = 15.sp
                            )
                        }
                        Text(
                            text = content.content,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Justify,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}