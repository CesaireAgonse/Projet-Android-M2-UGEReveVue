package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.LoginForm
import fr.uge.ugerevevueandroid.form.SignupForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CodePageInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.FilterInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.information.UserPageInformation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AllPermitService {

    // AuthenticationController
    @POST("signup")
    fun signup(@Body signupForm: SignupForm): Call<TokenForm>

    @POST("login")
    fun login(@Body loginForm: LoginForm): Call<TokenForm>

    @POST("refresh")
    fun refresh(@Header("Authorization") token: String): Call<TokenForm>

    @POST("logout")
    fun logout(): Call<String>

    // CodeController
    @GET("codes/{codeId}")
    fun code(@Path("codeId") codeId: Long): Call<CodeInformation>

    @GET("codes/filter")
    fun filter(
        @Query("sortBy") sortBy: String?,
        @Query("q") query: String?,
        @Query("pageNumber") pageNumber: Int?
    ): Call<FilterInformation>

    // PostController
    @GET("posts/comments/{postId}")
    fun comments(@Path("postId") postId: Long, @Query("pageNumber") pageNumber: Int?): Call<CommentPageInformation>

    @GET("posts/reviews/{postId}")
    fun reviews(@Path("postId") postId: Long, @Query("pageNumber") pageNumber: Int?): Call<ReviewPageInformation>

    // ReviewController
    @GET("reviews/{reviewId}")
    fun review(@Path("reviewId") reviewId: Long): Call<ReviewInformation>

    // UserController
    @GET("users/{username}")
    fun information(@Path("username") username: String): Call<UserInformation>

    @GET("users/reviews/{username}")
    fun reviewsFromUser(@Path("username") username: String, @Query("pageNumber") pageNumber: Int?): Call<ReviewPageInformation>

    @GET("users/comments/{username}")
    fun commentsFromUser(@Path("username") username: String, @Query("pageNumber") pageNumber: Int?): Call<CommentPageInformation>

    @GET("users/codes/{username}")
    fun codesFromUser(@Path("username") username: String, @Query("pageNumber") pageNumber: Int?): Call<CodePageInformation>

    @GET("users/followed/{username}")
    fun followedsFromUser(@Path("username") username: String, @Query("pageNumber") pageNumber: Int?): Call<UserPageInformation>
}