package fr.uge.ugerevevueandroid.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
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
fun ReviewPage(viewModel : MainViewModel){
    val scrollState = rememberScrollState()
    var contentNewComment by remember { mutableStateOf("") }
    var contentNewReview by remember { mutableStateOf("") }
    var review: ReviewInformation? by remember { mutableStateOf( null) }
    var commentPageInformation: CommentPageInformation? by remember { mutableStateOf( null) }
    var reviewPageInformation: ReviewPageInformation? by remember { mutableStateOf( null) }
    var id = viewModel.currentCodeToDisplay
    LaunchedEffect(true, id) {
        review = review(id)
        commentPageInformation = comments(id, 0)
        reviewPageInformation = reviews(id, 0)
    }
    if (review != null && commentPageInformation != null && reviewPageInformation != null){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(8.dp)
        ) {
            Review(review = review!!)
            commentPageInformation!!.comments.forEach{
                Comment(it)
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
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Comment")
            }

            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            Text(text = "Reviews about this post : ${review!!.reviews.size}")

            reviewPageInformation!!.reviews.forEach{
                Review(review = it, modifier = Modifier.clickable {
                    viewModel.changeCurrentCodeToDisplay(it.id)
                    id = it.id
                    viewModel.changeCurrentPage(Page.REVIEW)
                })
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
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Post")
            }
        }
    }
}