package fr.uge.ugerevevueandroid.page

import TokenManager
import android.app.Application
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.form.UpdatePasswordInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ApiService
import fr.uge.ugerevevueandroid.service.ImageManager
import fr.uge.ugerevevueandroid.service.allPermitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call


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
    Column (
        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
    ){
        val userState = remember { mutableStateOf<UserInformation?>(null) }

        LaunchedEffect(username) {
            val user = profile(username)
            userState.value = user
        }
        val user = userState.value
        if (user != null){
            UserDisplayer(application = application, viewModel = viewModel, user = user, Modifier.weight(4f))
            Column(
                modifier = Modifier.weight(6f)
                    .verticalScroll(scrollState)
            ) {
                Text(text = "Followed")
                user.followed?.forEach{
                    Text(
                        text = it.username,
                        modifier = Modifier.clickable {
                            viewModel.changeCurrentPage(Page.USER)
                            viewModel.changeCurrentUserToDisplay(it.username)
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun UserDisplayer(application: Application, viewModel : MainViewModel, user : UserInformation, modifier : Modifier = Modifier){
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
                if (user.profilePhoto != null) {
                    Image(
                        bitmap = ImageManager().byteArrayToImageBitMap(user.profilePhoto),
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
        val auth = TokenManager(application).getAuth()
        if (auth != null) {
            if (auth.username == user.username) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        selectImageLauncher.launch("image/*")
                    }) {
                        Text(text = "Change your profile image")
                    }
                    Button(onClick = {  viewModel.changeCurrentPage(Page.PASSWORD) }) {
                        Text(text = "Change your password")
                    }
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { follow(application = application, user.username) }) {
                        Text(text = "Follow")
                    }
                    Button(onClick = { unfollow(application = application, user.username) }) {
                        Text(text = "Unfollow")
                    }
                }
            }
        }
    }
}