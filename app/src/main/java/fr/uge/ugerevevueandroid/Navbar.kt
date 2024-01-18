package fr.uge.ugerevevueandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun Navbar(onClickButton: (Page) -> Unit){
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.navbar_color))
                .padding(1.dp)
        ) {
            IconButton(onClick = { onClickButton(Page.HOME) }, modifier = Modifier.padding(10.dp)) {
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

            Button(
                onClick = { onClickButton(Page.SIGNUP) },
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
                onClick = { onClickButton(Page.LOGIN) },
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

}
