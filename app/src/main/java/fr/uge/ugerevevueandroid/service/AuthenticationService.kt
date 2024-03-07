package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.LoginForm
import fr.uge.ugerevevueandroid.form.SignupForm
import fr.uge.ugerevevueandroid.form.TokenForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationService {
    @POST("signup")
    fun signup(@Body signupForm: SignupForm): Call<TokenForm>

    @POST("login")
    fun login(@Body loginForm: LoginForm): Call<TokenForm>

    @POST("refresh")
    fun refresh(@Header("Authorization") token: String): Call<TokenForm>

    @POST("logout")
    fun logout(): Call<String>
}