package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.UpdatePasswordInformation
import fr.uge.ugerevevueandroid.form.ReviewForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
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
}