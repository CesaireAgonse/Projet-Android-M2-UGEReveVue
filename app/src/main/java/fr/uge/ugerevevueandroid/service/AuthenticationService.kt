package fr.uge.ugerevevueandroid.service

import TokenManager
import android.app.Application
import fr.uge.ugerevevueandroid.form.LoginForm
import fr.uge.ugerevevueandroid.form.SignupForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.information.UserInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun signup(application: Application, username: String, password: String, confirmPassword:String) {
    return withContext(Dispatchers.IO){
        try {
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
        catch (e: Exception) {
        }
    }
}

suspend fun login(application: Application, username: String, password: String) : UserInformation? {
    withContext(Dispatchers.IO){
        try {
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
        catch (e: Exception) {
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

fun refresh(refreshToken:String): TokenForm? {
    val response = allPermitService.refresh(refreshToken).execute()
    if(response.isSuccessful){
       return response.body()
    }
    return null
}
