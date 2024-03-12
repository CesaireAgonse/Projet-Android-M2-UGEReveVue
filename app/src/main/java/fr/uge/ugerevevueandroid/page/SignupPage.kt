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
import fr.uge.ugerevevueandroid.form.SignupForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.allPermitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

suspend fun signup(application: Application, username: String, password: String, confirmPassword:String) {
    return withContext(Dispatchers.IO){
        val response = allPermitService.signup(SignupForm(username,password)).execute()
        if(response.isSuccessful){
            val userResponse = response.body()
            val manager = TokenManager(application)
            if(userResponse != null){
                manager.saveToken("bearer",userResponse.bearer)
                manager.saveToken("refresh",userResponse.refresh)
            }
        }
    }
}

@Composable
fun SignupPage(application: Application, viewModel: MainViewModel){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isComfirmPasswordVisible by remember { mutableStateOf(false) }
    var logged by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = logged ){
        if(logged){
            signup(application,username,password,confirmPassword)
            logged = false
            viewModel.changeCurrentPage(Page.HOME)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text= " Sign Up", fontSize = 40.sp)
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
            label = { Text(text = "Password", color = Color.LightGray) },
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
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Comfirm Passsword", color = Color.LightGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "password icon")
            },
            trailingIcon = {
                IconButton(onClick = { isComfirmPasswordVisible = !isComfirmPasswordVisible }) {
                    Icon(Icons.Default.Face, contentDescription = "")
                }
            },
            visualTransformation = if (isComfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
            Text(text = "Sign up")
        }
    }
}