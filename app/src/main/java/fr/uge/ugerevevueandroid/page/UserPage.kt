package fr.uge.ugerevevueandroid.page

import TokenManager
import UserAdmin
import android.app.Activity
import android.app.Application
import android.content.Intent
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.CodePageInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.information.UserPageInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.activity.CameraCaller
import fr.uge.ugerevevueandroid.manager.FileManager
import fr.uge.ugerevevueandroid.service.codesFromUser
import fr.uge.ugerevevueandroid.service.commentsFromUser
import fr.uge.ugerevevueandroid.service.follow
import fr.uge.ugerevevueandroid.service.followedsFromUser
import fr.uge.ugerevevueandroid.service.photo
import fr.uge.ugerevevueandroid.service.profile
import fr.uge.ugerevevueandroid.service.reviewsFromUser
import fr.uge.ugerevevueandroid.service.unfollow
import fr.uge.ugerevevueandroid.visual.Comment
import fr.uge.ugerevevueandroid.visual.Review
import fr.uge.ugerevevueandroid.visual.Code
import java.io.File

@Composable
fun UserPage(application: Application, viewModel : MainViewModel) {
    val scrollState = rememberScrollState()
    var username = viewModel.currentUserToDisplay
    var followedsFromUser: UserPageInformation? by remember {mutableStateOf( null)}
    var codesFromUser: CodePageInformation? by remember {mutableStateOf( null)}
    var reviewsFromUser: ReviewPageInformation? by remember {mutableStateOf( null)}
    var commentsFromUser: CommentPageInformation? by remember {mutableStateOf(null) }
    var user:UserInformation? by remember {mutableStateOf(null) }
    var pageNumberFolloweds by remember { mutableIntStateOf(0) }
    var pageNumberCodes by remember { mutableIntStateOf(0) }
    var pageNumberReviews by remember { mutableIntStateOf(0) }
    var pageNumberComments by remember { mutableIntStateOf(0) }
    Column (
        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
    ){

        LaunchedEffect(username, viewModel.triggerReloadPage) {
            user = profile(username)
            followedsFromUser = user?.let { followedsFromUser(it.username, pageNumberFolloweds) }
            codesFromUser = user?.let { codesFromUser(it.username, pageNumberCodes) }
            reviewsFromUser = user?.let { reviewsFromUser(it.username, pageNumberReviews) }
            commentsFromUser = user?.let { commentsFromUser(it.username, pageNumberComments) }
        }
        if (user != null){
            UserDisplayer(application = application, viewModel = viewModel, userI = user!!, Modifier.weight(4f))
            Column(
                modifier = Modifier
                    .weight(6f)
                    .verticalScroll(scrollState)
            ) {
                Text(text = "Followed:" + user!!.nbFollowed)
                if (followedsFromUser != null) {
                    followedsFromUser!!.users.forEach{
                        UserAdmin(application=application, user = it, viewModel =  viewModel)
                    }
                }
                Text(text = "Codes:" + user!!.nbCode)
                if (codesFromUser != null) {
                    codesFromUser!!.codes.forEach{
                        Code(application=application, codeInformation = it, modifier = Modifier.clickable {
                            viewModel.changeCurrentCodeToDisplay(it.id)
                            viewModel.changeCurrentPage(Page.CODE)
                        }, viewModel =  viewModel)
                    }
                }
                Text(text = "Reviews:" + user!!.nbReview)
                if (reviewsFromUser != null) {
                    reviewsFromUser!!.reviews.forEach{
                        Review(application=application, review = it, modifier = Modifier.clickable {
                            viewModel.changeCurrentCodeToDisplay(it.id)
                            viewModel.changeCurrentPage(Page.REVIEW)
                        }, viewModel)
                    }
                }
                Text(text = "Comments:" + user!!.nbComments)
                if (commentsFromUser != null) {
                    commentsFromUser!!.comments.forEach{
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
    var imageManager = FileManager();
    var user by remember { mutableStateOf(userI) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photoPath = result.data?.getStringExtra("photo")
            if (photoPath != null) {
                Log.i("PHOTO PATH", photoPath)
                uriUser = Uri.fromFile(File(photoPath))
            }
        }
    }
    LaunchedEffect(viewModel.triggerReloadPage){
        user = profile(viewModel.currentUserToDisplay)!!
    }
    val context = LocalContext.current
    LaunchedEffect(uriUser){
        if (uriUser != null){
            Log.i("TEST", uriUser.toString())
            imageManager.createMultipartFromUri(uriUser!!, application.contentResolver, "photo")
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
                        bitmap = FileManager().base64ToImageBitMap(user.profilePhoto),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.default_profile_image),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
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
            if (TokenManager(application).getAuth()?.role.equals("ADMIN")) {
                Button(
                    onClick = { viewModel.changeCurrentPage(Page.ADMIN)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(52, 152, 219),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 160.dp)
                        .align(Alignment.BottomCenter),
                    shape = CircleShape
                ) {
                    Text(text = "Admin page")
                }
            }
        }
        val auth = TokenManager(application).getAuth()
        if (auth != null) {
            if (auth.username == user.username) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
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
                        Button(onClick = {

                            cameraLauncher.launch(Intent(context, CameraCaller::class.java))

                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(52, 152, 219),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            shape = CircleShape
                        ) {
                            Text(text = "Take a picture for your profile image ")
                        }
                    }
                    Button(onClick = {  viewModel.changeCurrentPage(Page.PASSWORD) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(52, 152, 219),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = RectangleShape) {
                        Text(text = "Change your password")
                    }
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { unfollow(application = application, user.username) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(52, 152, 219),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = CircleShape) {
                        Text(text = "Unfollow")
                    }
                    Button(onClick = { follow(application = application, user.username) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(52, 152, 219),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        shape = CircleShape) {
                        Text(text = "Follow")
                    }

                }
            }
        }
    }
}
