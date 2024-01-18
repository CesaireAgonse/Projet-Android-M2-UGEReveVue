package fr.uge.ugerevevueandroid

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

const val url = "http://10.0.2.2:8080/api/v1/"
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(url)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val apiService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @POST("users/signup")
    fun signup(@Body signupForm: SignupForm): Call<SignupForm>


}
