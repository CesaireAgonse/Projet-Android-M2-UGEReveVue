package fr.uge.ugerevevueandroid.page

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Définir une interface pour la gestion de la sélection d'image
suspend fun profile(application: Application, username: String): UserInformation? {
    return withContext(Dispatchers.IO) {
        val response = ApiService(application).getUserService().getUserInformation(username).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
@Composable
fun UserPage(viewModel : MainViewModel, application: Application) {
    //val scrollState = rememberScrollState()
    var username = "a"
    Column (
        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
    ){
        val userState = remember { mutableStateOf<UserInformation?>(null) }

        LaunchedEffect(username) {
            val user = profile(application, username)
            userState.value = user
        }
        val user = userState.value
        if (user != null){
            UserDisplayer(viewModel = viewModel, user = user, Modifier.weight(4f))
        }
//        Column(
//            modifier = Modifier.weight(6f)
//                .verticalScroll(scrollState)
//        ) {
//            Text(text = "Follower")
//            viewModel.currentUserToDisplay.followed?.forEach{
//                Text(
//                    text = "${it.username}",
//                    modifier = Modifier.clickable {
//                        viewModel.changeCurrentPage(Page.USER)
//                        viewModel.changeCurrentUserToDisplay(it)
//                    }
//                )
//            }
//        }
    }

}

@Composable
fun UserDisplayer(viewModel : MainViewModel, user : UserInformation, modifier : Modifier = Modifier){
    var uriUser: Uri? = null
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
                Image(
                    painter = painterResource(id = R.drawable.default_profile_image),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape) // Forme ronde
                )
                Text(
                    text = "${user.username}",
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 30.sp
                )
                Text(
                    text = "${user.followed?.size} follows",
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 18.sp
                )
            }
            if (user.isAdmin) {
                Button(
                    onClick = { viewModel.changeCurrentPage(Page.ADMIN)
                    },
                    modifier = Modifier
                        .padding(top = 160.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(text = "Admin page")
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                selectImageLauncher.launch("image/*")
            }) {
                Text(text = "Change your profile image")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Change your password")
            }
        }
    }
}