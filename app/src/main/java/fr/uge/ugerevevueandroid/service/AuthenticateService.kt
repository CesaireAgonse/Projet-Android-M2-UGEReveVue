package fr.uge.ugerevevueandroid.service

import android.graphics.Bitmap
import fr.uge.ugerevevueandroid.form.CodeForm
import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.UpdatePasswordInformation
import fr.uge.ugerevevueandroid.form.ReviewForm
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthenticateService {

    // CodeController

    // PostController
    @POST("posts/vote/{postId}")
    fun postVoted(@Path("postId") postId: Long, @Query("voteType") voteType:String): Call<Long>

    @POST("posts/comment/{postId}")
    fun postCommented(@Path("postId") postId:Long, @Body commentForm: CommentForm): Call<Void>

    @POST("posts/review/{postId}")
    fun postReviewed(@Path("postId") postId:Long, @Body reviewForm: ReviewForm): Call<Void>

    // ReviewController

    // UserController
    @POST("users/password")
    fun password(@Body updatePasswordInformation: UpdatePasswordInformation): Call<Void>

    @POST("users/follow/{username}")
    fun follow(@Path("username") username: String): Call<Void>

    @POST("users/unfollow/{username}")
    fun unfollow(@Path("username") username: String): Call<Void>

    @Multipart
    @POST("users/photo")
    fun photo(@Part photo: MultipartBody.Part): Call<Void>

    @Multipart
    @POST("codes/create")
fun create(@Part("title") title: String,
                   @Part("description") description: String,
                   @Part javaFile: MultipartBody.Part,
                   @Part unitFile: MultipartBody.Part): Call<Void>
}