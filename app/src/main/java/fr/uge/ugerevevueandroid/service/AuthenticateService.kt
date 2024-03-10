package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.form.CommentForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticateService {

    // CodeController

    // PostController
    @POST("posts/comment/{postId}")
    fun postCommented(@Path("postId") postId:Long, @Body commentForm: CommentForm): Call<Void>
    // ReviewController

    // UserController

}