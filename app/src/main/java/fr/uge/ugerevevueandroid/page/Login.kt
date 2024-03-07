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
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.authenticationService
import retrofit2.Call

fun login(application: Application, username: String, password: String) {
    val loginForm = LoginForm(username, password)
    val call = authenticationService.login(loginForm)
    call.enqueue(object : retrofit2.Callback<TokenForm> {
        override fun onResponse(call: Call<TokenForm>, response: retrofit2.Response<TokenForm>) {
            if (response.isSuccessful){
                val userResponse = response.body()
                val tokenManager = TokenManager(application)
                if (userResponse != null) {
                    tokenManager.saveToken("bearer", userResponse.bearer)
                    tokenManager.saveToken("refresh", userResponse.refresh)
                }
            } else {
                Log.i("test", "test2")
            }
        }
        override fun onFailure(call: Call<TokenForm>, t: Throwable) {
            Log.i("test3", t.message.orEmpty())
        }
    })
}
@Composable
fun LoginPage(application: Application, viewModel: MainViewModel){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
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
            }
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
                login(application = application, username = username, password = password)
                viewModel.changeCurrentPage(Page.HOME)
            },
            modifier = Modifier
        ){
            Text(text = "Log in")
        }
    }
}
