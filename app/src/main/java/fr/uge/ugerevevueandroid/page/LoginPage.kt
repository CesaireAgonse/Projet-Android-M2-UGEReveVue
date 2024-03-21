package fr.uge.ugerevevueandroid.page

import TokenManager
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.form.LoginForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.allPermitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

suspend fun login(application: Application, username: String, password: String) : UserInformation? {
    withContext(Dispatchers.IO){
        val response = allPermitService.login(LoginForm(username,password)).execute()
        if(response.isSuccessful){
            val userResponse = response.body()
            val manager = TokenManager(application)
            if(userResponse != null){
                manager.saveToken("bearer",userResponse.bearer)
                manager.saveToken("refresh",userResponse.refresh)
            }
        }
    }
    return withContext(Dispatchers.IO) {
        val response = allPermitService.information(username).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun changeLoggedUser(username: String): UserInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.information(username).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

@Composable
fun LoginPage(application: Application, viewModel: MainViewModel){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var logged by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = logged ){
        if(logged){
            val user = login(application,username,password)
            logged = false
            if (user != null){
                viewModel.changeCurrentUserLogged(user)
            }
            viewModel.changeCurrentPage(Page.HOME)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text= " Log In", fontSize = 40.sp)
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Username", color = Color.LightGray) },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "username icon")
            },

        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Passsword", color = Color.LightGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "password icon")
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(Icons.Default.Face, contentDescription = "")
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        Button(
            onClick = {
                logged = true
            },
            modifier = Modifier
        ){
            Text(text = "Log in")
        }
    }
}
