package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.LoginForm
import fr.uge.ugerevevueandroid.form.SignupForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.FilterInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.information.UserPageInformation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AdminPermitService {

    // CodeController
    @DELETE("codes/{codeId}")
    fun codeDeleted(@Path("codeId") codeId: Long): Call<Void>

    // ReviewController
    @DELETE("reviews/{reviewId}")
    fun reviewDeleted(@Path("reviewId") reviewId: Long): Call<Void>

    // CommentController
    @DELETE("comments/{commentId}")
    fun commentDeleted(@Path("commentId") commentId: Long): Call<Void>

    // UserController
    @DELETE("users/{username}")
    fun userDeleted(@Path("username") username: String): Call<Void>

    @GET("users/all")
    fun getAllUsers(@Query("pageNumber") pageNumber: Int?): Call<UserPageInformation>
}