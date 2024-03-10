package fr.uge.ugerevevueandroid.visual

import TokenManager
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page
import fr.uge.ugerevevueandroid.service.allPermitService

@Composable
fun Navbar(application: Application, viewModel: MainViewModel){
    val manager =  TokenManager(application)
    val isConnected = manager.hasBearer()
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.navbar_color))
            .padding(1.dp)
    ) {
        IconButton(onClick = { viewModel.changeCurrentPage(Page.HOME) }, modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home button",
                modifier = Modifier.fillMaxSize(),
                tint = colorResource(id = R.color.nav_button_color)
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search button",
                modifier = Modifier.fillMaxSize(),
                tint = colorResource(id = R.color.nav_button_color)
            )
        }
        if (!isConnected){
            Button(
                onClick = {viewModel.changeCurrentPage(Page.SIGNUP) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_color),
                    contentColor = colorResource(id = R.color.button_color_2)
                ),
                modifier = Modifier
                    .padding(7.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.button_color))
                    .height(42.dp)

            ) {
                Text(text = "Sign up", modifier = Modifier.padding(2.dp))
            }
            Button(
                onClick = { viewModel.changeCurrentPage(Page.LOGIN) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.navbar_color),
                    contentColor = colorResource(id = R.color.button_login_color)
                ),
                modifier = Modifier
                    .padding(7.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.navbar_color))
                    .border(2.dp, colorResource(id = R.color.button_login_color))
                    .height(42.dp)

            ) {
                Text(text = "Log in", modifier = Modifier.padding(1.dp))
            }
        }
        else {
            Image(
                painter = painterResource(id = R.drawable.default_profile_image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .clickable {
                        viewModel.changeCurrentPage(Page.USER)
                        manager.getAuth()?.let { viewModel.changeCurrentUserToDisplay(it.username) }
                    }
                    .size(75.dp)
                    .clip(CircleShape)
            )
            Button(
                onClick = {
                    allPermitService.logout()
                    manager.clearToken("bearer")
                    manager.clearToken("refresh")
                    viewModel.changeCurrentPage(Page.HOME)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_color),
                    contentColor = colorResource(id = R.color.button_color_2)
                ),
                modifier = Modifier
                    .padding(7.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.button_color))
                    .height(42.dp)

            ) {
                Text(text = "Log out", modifier = Modifier.padding(2.dp))
            }
        }
    }
}
