package fr.uge.ugerevevueandroid.page

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
import fr.uge.ugerevevueandroid.form.UpdatePasswordInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ApiService
import retrofit2.Call

fun password(application: Application, currentPassword: String, newPassword: String, confirmPassword:String) {
    val call = ApiService(application).authenticateService()
        .password(UpdatePasswordInformation(currentPassword, newPassword))
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
fun PasswordPage(application: Application, viewModel: MainViewModel){
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isCurrentPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isComfirmPasswordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text= "Update Password", fontSize = 40.sp)
        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Current Password", color = Color.LightGray) },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "password icon")
            },
            trailingIcon = {
                IconButton(onClick = { isCurrentPasswordVisible = !isCurrentPasswordVisible }) {
                    Icon(Icons.Default.Face, contentDescription = "")
                }
            }
        )
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "New Password", color = Color.LightGray) },
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
                password(application = application, currentPassword, newPassword, confirmPassword)
                viewModel.changeCurrentPage(Page.USER)
            },
            modifier = Modifier
        ){
            Text(text = "Update Password")
        }
    }
}