package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.SignupForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.form.UpdatePasswordForm
import fr.uge.ugerevevueandroid.information.UserInformation
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("users/password")
    fun updatePassword(@Body updatePasswordForm: UpdatePasswordForm): Call<Void>

    @GET("users/{username}")
     fun getUserInformation(@Path("username") username: String): Call<UserInformation>

    @POST("users/follow/{username}")
    fun followUser(@Path("username") username: String): Call<Void>

    @POST("users/unfollow/{username}")
    fun unfollowUser(@Path("username") username: String): Call<Void>

    @DELETE("users/delete/{userId}")
    fun deleteUser(@Path("userId") userId: Long): Call<Void>
}