package fr.uge.ugerevevueandroid.page

import TokenManager
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.form.UpdatePasswordInformation
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CodePageInformation
import fr.uge.ugerevevueandroid.information.CommentInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.information.UserPageInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ApiService
import fr.uge.ugerevevueandroid.service.ImageManager
import fr.uge.ugerevevueandroid.service.allPermitService
import fr.uge.ugerevevueandroid.visual.Code
import fr.uge.ugerevevueandroid.visual.Comment
import fr.uge.ugerevevueandroid.visual.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Call


suspend fun photo(application: Application, photo: MultipartBody.Part) {
    return withContext(Dispatchers.IO) {
        ApiService(application).authenticateService().photo(photo).execute();
    }
}

suspend fun profile(username: String): UserInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.information(username).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
suspend fun codesFromUser(username: String, pageNumber: Int): CodePageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.codesFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
suspend fun reviewsFromUser(username: String, pageNumber: Int): ReviewPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.reviewsFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun commentsFromUser(username: String, pageNumber: Int): CommentPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.commentsFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

fun follow(application: Application, username: String) {
    val call = ApiService(application).authenticateService()
        .follow(username)
    call.enqueue(object : retrofit2.Callback<Void> {
        override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
            if (response.isSuccessful){
                Log.i("isSuccessful", "isSuccessful")
            } else {
                Log.i("isNotSuccessful", "isNotSuccessful")
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.i("onFailure", t.message.orEmpty())
        }
    })
}

fun unfollow(application: Application, username: String) {
    val call = ApiService(application).authenticateService()
        .unfollow(username)
    call.enqueue(object : retrofit2.Callback<Void> {
        override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
            if (response.isSuccessful){
                Log.i("isSuccessful", "isSuccessful")
            } else {
                Log.i("isNotSuccessful", "isNotSuccessful")
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.i("onFailure", t.message.orEmpty())
        }
    })
}

@Composable
fun UserPage(application: Application, viewModel : MainViewModel) {
    val scrollState = rememberScrollState()
    var username = viewModel.currentUserToDisplay
    var codesFromUsers: CodePageInformation? by remember {mutableStateOf( null)}
    var reviewsFromUsers: ReviewPageInformation? by remember {mutableStateOf( null)}
    var commentsFromUsers: CommentPageInformation? by remember {mutableStateOf(null) }
    var pageNumberCodes by remember { mutableIntStateOf(0) }
    var pageNumberReviews by remember { mutableIntStateOf(0) }
    var pageNumberComments by remember { mutableIntStateOf(0) }
    Column (
        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
    ){
        val userState = remember { mutableStateOf<UserInformation?>(null) }

        LaunchedEffect(username, viewModel.triggerReloadPage) {
            val user = profile(username)
            userState.value = user
            codesFromUsers = codesFromUser(user!!.username, pageNumberCodes)
            reviewsFromUsers = reviewsFromUser(user!!.username, pageNumberReviews)
            commentsFromUsers = commentsFromUser(user!!.username, pageNumberComments)
        }
        val user = userState.value
        if (user != null){
            UserDisplayer(application = application, viewModel = viewModel, userI = user, Modifier.weight(4f))
            Column(
                modifier = Modifier
                    .weight(6f)
                    .verticalScroll(scrollState)
            ) {
                Text(text = "Followed:" + user.nbFollowed)
                Text(text = "Codes:" + user.nbCode)
                if (codesFromUsers != null) {
                    codesFromUsers!!.codes.forEach{
                        Code(application=application, codeInformation = it, modifier = Modifier.clickable {
                            viewModel.changeCurrentCodeToDisplay(it.id)
                            viewModel.changeCurrentPage(Page.CODE)
                        }, viewModel =  viewModel)
                    }
                }
                Text(text = "Reviews:" + user.nbReview)
                if (reviewsFromUsers != null) {
                    reviewsFromUsers!!.reviews.forEach{
                        Review(application=application, review = it, modifier = Modifier.clickable {
                            viewModel.changeCurrentCodeToDisplay(it.id)
                            viewModel.changeCurrentPage(Page.REVIEW)
                        }, viewModel)
                    }
                }
                Text(text = "Comments:" + user.nbComment)
                if (commentsFromUsers != null) {
                    commentsFromUsers!!.comments.forEach{
                        Comment(viewModel, application, it)
                    }
                }
            }
        }
    }

}

@Composable
fun UserDisplayer(application: Application, viewModel : MainViewModel, userI : UserInformation, modifier : Modifier = Modifier){
    var uriUser: Uri? by remember { mutableStateOf(null) }
    var imageManager = ImageManager();
    var user by remember {
        mutableStateOf(userI)
    }
    LaunchedEffect(uriUser){
        if (uriUser != null){
            imageManager.createMultipartFromUri(uriUser!!, application.contentResolver)
                ?.let { photo(application, it) }
            var temp = profile(user.username)
            if (temp != null){
                user = temp
                viewModel.changeCurrentUserLogged(temp)
            }
        }
    }

    val selectImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            Log.i("image", "l'uri de la nouvelle image est : ${uri}")
            uriUser = uri
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                if (user.profilePhoto != null) {
                    Image(
                        bitmap = ImageManager().base64ToImageBitMap(user.profilePhoto),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape) // Forme ronde
                    )
                } else {
                    // Afficher une image par défaut si le profilePhoto est null ou vide
                    Image(
                        painter = painterResource(id = R.drawable.default_profile_image),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape) // Forme ronde
                    )
                }
                Text(
                    text = user.username,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 30.sp
                )
                Text(
                    text = "${user.nbFollowed} follows",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 18.sp
                )
            }
        }
        val auth = TokenManager(application).getAuth()
        if (auth != null) {
            if (auth.username == user.username) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        selectImageLauncher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color(52, 152, 219),
                    contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    shape = CircleShape
                    ) {
                        Text(text = "Change your profile image")
                    }
                    Button(onClick = {  viewModel.changeCurrentPage(Page.PASSWORD) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(52, 152, 219),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = CircleShape) {
                        Text(text = "Change your password")
                    }
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { follow(application = application, user.username) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(52, 152, 219),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = CircleShape) {
                        Text(text = "Follow")
                    }
                    Button(onClick = { unfollow(application = application, user.username) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(52, 152, 219),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = CircleShape) {
                        Text(text = "Unfollow")
                    }
                }
            }
        }
    }
}