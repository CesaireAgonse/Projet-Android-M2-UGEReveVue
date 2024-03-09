package fr.uge.ugerevevueandroid.page

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.model.MainViewModel

@Composable
fun CodePage(viewModel : MainViewModel, modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()
    var contentNewComment by remember { mutableStateOf("") }
    var contentNewReview by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Code(code = viewModel.currentCodeToDisplay)
        if (viewModel.currentCodeToDisplay.comments != null){
            viewModel.currentCodeToDisplay.comments.forEach{
                Comment(it)
            }
        }
        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp))
        OutlinedTextField(
            value = contentNewComment,
            onValueChange = { contentNewComment = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Your comment here", color = Color.LightGray) }
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Comment")
        }

        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
        if (viewModel.currentCodeToDisplay.review != null){
            Text(text = "Reviews about this post : ${viewModel.currentCodeToDisplay.review.size}")

            viewModel.currentCodeToDisplay.review.forEach{
                Review(review = it)
            }
        }
        OutlinedTextField(
            value = contentNewReview,
            onValueChange = { contentNewReview = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Your review here", color = Color.LightGray) }
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Post")
        }
    }
}

@Composable
fun Code(code : CodeInformation){
    var voteButtonClicked : Boolean by remember { mutableStateOf(false) }
    Text(
        text = "${code.title}",
        fontSize = 30.sp
    )
    Row {
        Text(
            text = "from : ${code.userInformation.username}",
            modifier = Modifier.clickable {  }
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "${code.date}")
    }
    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp))

    Row {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ voteButtonClicked = !voteButtonClicked},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = colorResource(id = R.color.button_color_2)
                ),
                shape = CircleShape
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "UpVote")
            }
            Text(text = "${code.score}")
            Button(onClick = { /*TODO*/ voteButtonClicked = !voteButtonClicked},
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
            Text(text = code.description,
                textAlign = TextAlign.Justify)

            Text(text = code.javaContent)
            if (code.unitContent != null){
                Text(text = code.unitContent)
            }
        }
    }
}

@Composable
fun Comment(comment: CommentInformation){
    Surface(
        shadowElevation = 8.dp,
        border = BorderStroke(0.dp, Color.Gray),
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        contentColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = comment.userInformation.username,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /*TODO*/}
            )
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            Text(text = comment.content)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "${comment.date}")
            }
        }
    }
}

@Composable
fun Review(review: ReviewInformation){
    var voteButtonClicked : Boolean by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier.padding(2.dp)
        // mettre un petit background et delimiter chaque component
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(text = "reviews: ${review.reviews.size}",
                fontSize = 10.sp)
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(text = "comments: ${review.comments.size}",
                fontSize = 10.sp)
        }

        Row {
            Text(
                text = "from : ${review.userInformation.username}",
                modifier = Modifier.clickable { /*User(user = code.userInformation)*/
                    /*
                    redirection(Page.USER)
                    setUser(code.userInformation)
                    */
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "${review.date}")
        }


        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { /*TODO*/ voteButtonClicked = !voteButtonClicked},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color),
                        contentColor = Color.Black,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = colorResource(id = R.color.button_color_2)
                    ),
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "UpVote")
                }
                Text(text = "${review.score}")
                Button(onClick = { /*TODO*/ voteButtonClicked = !voteButtonClicked},
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
                    Text(
                        text = review.content,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Justify,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}