package fr.uge.ugerevevueandroid.page

import TokenManager
import android.app.Application
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
import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.ReviewForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ApiService
import fr.uge.ugerevevueandroid.service.allPermitService
import fr.uge.ugerevevueandroid.visual.Code
import fr.uge.ugerevevueandroid.visual.Comment
import fr.uge.ugerevevueandroid.visual.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun code(codeId: Long): CodeInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.code(codeId).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun comments(postId: Long, pageNumber: Int): CommentPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.comments(postId, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun reviews(postId: Long, pageNumber: Int): ReviewPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.reviews(postId, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun postCommented(application:Application, postId: Long, commentForm: CommentForm) {
    return withContext(Dispatchers.IO) {
        ApiService(application).authenticateService()
            .postCommented(postId, commentForm)
            .execute()
    }
}

suspend fun postReviewed(application:Application, postId: Long, reviewForm: ReviewForm) {
    return withContext(Dispatchers.IO) {
        ApiService(application).authenticateService()
            .postReviewed(postId, reviewForm)
            .execute()
    }
}

@Composable
fun CodePage(application: Application, viewModel : MainViewModel){
    val scrollState = rememberScrollState()
    var contentNewComment by remember { mutableStateOf("") }
    var contentNewReview by remember { mutableStateOf("") }
    var code:CodeInformation? by remember {mutableStateOf( null)}
    var commentPageInformation:CommentPageInformation? by remember {mutableStateOf( null)}
    var reviewPageInformation: ReviewPageInformation? by remember {mutableStateOf( null)}
    var commented by remember { mutableStateOf(false) }
    var reviewed by remember { mutableStateOf(false) }
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

    LaunchedEffect(true, commented) {
        code = code(viewModel.currentCodeToDisplay)
        commentPageInformation = comments(viewModel.currentCodeToDisplay, 0)
        reviewPageInformation = reviews(viewModel.currentCodeToDisplay, 0)
    }
    if (code != null && commentPageInformation != null && reviewPageInformation != null){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(8.dp)
        ) {
            Code(application=application, code = code!!)
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
            Button(onClick = {
                commented = true
            }) {
                Text(text = "Comment")
            }

            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            Text(text = "Reviews about this post : ${code!!.reviews.size}")

            reviewPageInformation!!.reviews.forEach{
                Review(application=application, review = it, modifier = Modifier.clickable {
                    viewModel.changeCurrentCodeToDisplay(it.id)
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
            Button(onClick = { reviewed=true }) {
                Text(text = "Post")
            }
        }
    }
}