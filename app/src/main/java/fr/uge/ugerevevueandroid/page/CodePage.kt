package fr.uge.ugerevevueandroid.page

import TokenManager
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.ReviewForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.code
import fr.uge.ugerevevueandroid.service.comments
import fr.uge.ugerevevueandroid.service.postCommented
import fr.uge.ugerevevueandroid.service.postReviewed
import fr.uge.ugerevevueandroid.service.reviews
import fr.uge.ugerevevueandroid.visual.Code
import fr.uge.ugerevevueandroid.visual.Comment
import fr.uge.ugerevevueandroid.visual.Review

@Composable
fun CodePage(application: Application, viewModel : MainViewModel){
    val scrollState = rememberScrollState()
    var contentNewComment by remember { mutableStateOf("") }
    var contentNewReview by remember { mutableStateOf("") }
    var titleNewReview by remember { mutableStateOf("") }
    var code:CodeInformation? by remember {mutableStateOf( null)}
    var commentPageInformation:CommentPageInformation? by remember {mutableStateOf( null)}
    var reviewPageInformation: ReviewPageInformation? by remember {mutableStateOf( null)}
    var commented by remember { mutableStateOf(false) }
    var reviewed by remember { mutableStateOf(false) }
    var pageNumberComments by remember { mutableIntStateOf(0) }
    var pageNumberReviews by remember { mutableIntStateOf(0) }
    LaunchedEffect(commented) {
        if (commented){
            postCommented(application, viewModel.currentCodeToDisplay, CommentForm(contentNewComment, ""))
            commented = false;
            contentNewComment = ""
        }
    }
    LaunchedEffect(reviewed) {
        if (reviewed){
            postReviewed(application, viewModel.currentCodeToDisplay, ReviewForm(titleNewReview, listOf(
                CommentForm(contentNewReview, ""))))
            reviewed = false;
            contentNewReview = ""
            titleNewReview = ""
        }
    }
    LaunchedEffect(true, commented,reviewed,pageNumberComments,pageNumberReviews,viewModel.triggerReloadPage) {
        code = code(viewModel.currentCodeToDisplay)
        commentPageInformation = comments(viewModel.currentCodeToDisplay, pageNumberComments)
        reviewPageInformation = reviews(viewModel.currentCodeToDisplay, pageNumberReviews)
    }
    if (code != null && commentPageInformation != null && reviewPageInformation != null){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Code(application=application, codeInformation = code!!,viewModel)
            Text(text = "Comments about this post : ${code!!.comments}", fontWeight = FontWeight.Bold)

            commentPageInformation!!.comments.forEach{
                Comment(viewModel, application, it)
            }
            Row{
                if(pageNumberComments >= 1){
                    Button(
                        onClick = {pageNumberComments--},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RectangleShape,
                    ) {
                        Text(text = "Previous")
                    }
                }
                if(pageNumberComments < commentPageInformation!!.maxPageNumber) {
                    Button(
                        onClick = { pageNumberComments++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RectangleShape,
                    ) {
                        Text(text = "Next")
                    }
                }
            }
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            if (TokenManager(application).getAuth() != null){
                OutlinedTextField(
                    value = contentNewComment,
                    onValueChange = { contentNewComment = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    label = { Text(text = "Your comment here", color = Color.LightGray) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                Button(onClick = {commented = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(52, 152, 219),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    shape = RectangleShape,
                ) {
                    Text(text = "Comment")
                }
                Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp))
            }
            Text(text = "Reviews about this post : ${code!!.reviews}", fontWeight = FontWeight.Bold)
            reviewPageInformation!!.reviews.forEach{
                Review(application=application, review = it, modifier = Modifier.clickable {
                    viewModel.changeCurrentCodeToDisplay(it.id)
                    viewModel.changeCurrentPage(Page.REVIEW)
                }, viewModel)
            }
            Row{
                if(pageNumberReviews >= 1){
                    Button(onClick = {pageNumberReviews--},
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RectangleShape,
                    ) {
                        Text(text = "Previews")
                    }
                }
                if(pageNumberReviews < reviewPageInformation!!.maxPageNumber) {
                    Button(
                        onClick = { pageNumberReviews++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RectangleShape,
                    ) {
                        Text(text = "Next")
                    }
                }
            }
            if (TokenManager(application).getAuth() != null) {
                OutlinedTextField(
                    value = titleNewReview,
                    onValueChange = { titleNewReview = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    label = { Text(text = "Your title here", color = Color.LightGray) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = contentNewReview,
                    onValueChange = { contentNewReview = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    label = { Text(text = "Your review here", color = Color.LightGray) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                Button(
                    onClick = { reviewed = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(52, 152, 219),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    shape = RectangleShape,
                ) {
                    Text(text = "Review")
                }
            }
        }
    }
}