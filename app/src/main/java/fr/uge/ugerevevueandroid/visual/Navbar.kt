package fr.uge.ugerevevueandroid.visual

import TokenManager
import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.service.ImageManager

@Composable
fun SearchBar(viewModel: MainViewModel) {
    var query by remember { mutableStateOf("") }
    TextField(
        value = query,
        onValueChange = { query = it.filter { letter -> letter != '\n'}; viewModel.changeCurrentQuery(query) },
        placeholder = { Text("Type your query", fontSize = 15.sp) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    )
}

@Composable
fun Navbar(application: Application, viewModel: MainViewModel){
    val manager =  TokenManager(application)
    val isConnected = manager.hasBearer()
    var searchBarOn by remember { mutableStateOf(false) }
    var height by remember { mutableStateOf(60.dp) }
    var logged by remember { mutableStateOf(false) }
    var userPhoto by remember {mutableStateOf("")}
    LaunchedEffect(key1 = logged){
        if(logged){
            logged = false
            manager.clearToken("bearer")
            manager.clearToken("refresh")
            viewModel.photoURL = null;
            viewModel.changeCurrentUserLogged(UserInformation("", "",   0, 0,0, 0,false))
            viewModel.changeCurrentPage(Page.HOME)
        }
    }
    LaunchedEffect(key1 = viewModel.currentUserLogged.profilePhoto){
        userPhoto = viewModel.currentUserLogged.profilePhoto
    }
    Column(modifier = Modifier
        .height(height)
        .fillMaxWidth()
        .background(colorResource(id = R.color.navbar_color))
        .padding(1.dp)
    ) {
        Row {
            IconButton(onClick = {
                if (viewModel.currentPage == Page.HOME){
                    viewModel.reloadPage()
                }
                viewModel.changeCurrentPage(Page.HOME) }, modifier = Modifier.padding(10.dp)) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home button",
                    modifier = Modifier.fillMaxSize(),
                    tint = colorResource(id = R.color.nav_button_color)
                )
            }
            IconButton(onClick = { searchBarOn = !searchBarOn }, modifier = Modifier.padding(10.dp)) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search button",
                    modifier = Modifier.fillMaxSize(),
                    tint = colorResource(id = R.color.nav_button_color)
                )
            }
            if (!isConnected){
                Button(
                    onClick = { viewModel.changeCurrentPage(Page.LOGIN) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.padding(4.dp),
                    shape = RectangleShape

                ) {
                    Text(text = "Log in", modifier = Modifier.padding(1.dp))
                }
                Button(
                    onClick = {viewModel.changeCurrentPage(Page.SIGNUP) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier.padding(4.dp),
                    shape = RectangleShape
                ) {
                    Text(text = "Sign up", modifier = Modifier.padding(2.dp))
                }
            }
            else {
                if (userPhoto != null && userPhoto != "") {
                    Image(
                        bitmap = ImageManager().base64ToImageBitMap(userPhoto),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .clickable {
                                if (viewModel.currentPage == Page.USER){
                                    viewModel.reloadPage()
                                }
                                viewModel.changeCurrentPage(Page.USER)
                                manager
                                    .getAuth()
                                    ?.let { viewModel.changeCurrentUserToDisplay(it.username) }
                            }
                            .size(75.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.default_profile_image),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier
                            .clickable {
                                viewModel.changeCurrentPage(Page.USER)
                                manager
                                    .getAuth()
                                    ?.let { viewModel.changeCurrentUserToDisplay(it.username) }
                            }
                            .size(75.dp)
                    )
                }
                Button(
                    onClick = {
                        logged = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.padding(4.dp),
                    shape = RectangleShape

                ) {
                    Text(text = "Log out", modifier = Modifier.padding(2.dp))
                }
            }
        }
        Row() {
            if (searchBarOn) {
                height = 130.dp
                SearchBar(viewModel);
            }
            else {
                height = 60.dp
            }
        }
    }
}
