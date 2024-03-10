package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.ReviewForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticateService {

    // CodeController

    // PostController
    @POST("posts/comment/{postId}")
    fun postCommented(@Path("postId") postId:Long, @Body commentForm: CommentForm): Call<Void>

    @POST("posts/review/{postId}")
    fun postReviewed(@Path("postId") postId:Long, @Body reviewForm: ReviewForm): Call<Void>

    // ReviewController

    // UserController

}