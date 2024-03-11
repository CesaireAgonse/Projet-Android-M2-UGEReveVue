package fr.uge.ugerevevueandroid.page

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.ReviewForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.allPermitService
import fr.uge.ugerevevueandroid.visual.Code
import fr.uge.ugerevevueandroid.visual.Comment
import fr.uge.ugerevevueandroid.visual.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun review(reviewId: Long): ReviewInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.review(reviewId).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
@Composable
fun ReviewPage(application: Application, viewModel : MainViewModel){
    val scrollState = rememberScrollState()
    var contentNewComment by remember { mutableStateOf("") }
    var contentNewReview by remember { mutableStateOf("") }
    var review: ReviewInformation? by remember { mutableStateOf( null) }
    var commentPageInformation: CommentPageInformation? by remember { mutableStateOf( null) }
    var reviewPageInformation: ReviewPageInformation? by remember { mutableStateOf( null) }
    var id = viewModel.currentCodeToDisplay
    var commented by remember { mutableStateOf(false) }
    var reviewed by remember { mutableStateOf(false) }
    var pageNumberComments by remember {
        mutableIntStateOf(0)
    }
    var pageNumberReviews by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(commented) {
        if (commented){
            postCommented(application, viewModel.currentCodeToDisplay, CommentForm(contentNewComment))
            commented = false;
            contentNewComment = ""
        }
    }
    LaunchedEffect(reviewed) {
        if (reviewed){
            postReviewed(application, viewModel.currentCodeToDisplay, ReviewForm(contentNewReview))
            reviewed = false;
            contentNewReview = ""
        }
    }
    LaunchedEffect(true, pageNumberComments,pageNumberReviews,commented,reviewed) {
        review = review(id)
        commentPageInformation = comments(id, pageNumberComments)
        reviewPageInformation = reviews(id, pageNumberReviews)
    }
    if (review != null && commentPageInformation != null && reviewPageInformation != null){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(8.dp)
        ) {
            Review(application=application, review = review!!)
            commentPageInformation!!.comments.forEach{
                Comment(it)
            }
            Row{
                if(pageNumberComments >= 1){
                    Button(onClick = {pageNumberComments--}) {
                        Text(text = "Previews")
                    }
                }
                Button(onClick = {pageNumberComments++}) {
                    Text(text = "Next")
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
                label = { Text(text = "Your comment here", color = Color.LightGray) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
            Button(onClick = { commented=true }) {
                Text(text = "Comment")
            }

            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            Text(text = "Reviews about this post : ${review!!.reviews.size}")

            reviewPageInformation!!.reviews.forEach{
                Review(application=application, review = it, modifier = Modifier.clickable {
                    viewModel.changeCurrentCodeToDisplay(it.id)
                    id = it.id
                    viewModel.changeCurrentPage(Page.REVIEW)
                })
            }
            Row{
                if(pageNumberReviews >= 1){
                    Button(onClick = {pageNumberReviews--}) {
                        Text(text = "Previews")
                    }
                }
                Button(onClick = {pageNumberReviews++}) {
                    Text(text = "Next")
                }
            }
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
            Button(onClick = { reviewed=true }) {
                Text(text = "Post")
            }
        }
    }
}