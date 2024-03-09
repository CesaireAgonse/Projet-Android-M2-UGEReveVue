package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.FilterInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilterService {
    @GET("codes/filter")
    fun filter(
        @Query("sortBy") sortBy: String?,
        @Query("q") query: String?,
        @Query("pageNumber") pageNumber: Int?
    ): Call<FilterInformation>

    @GET("codes/{codeId}")
    fun code(@Path("codeId") codeId: Long): Call<CodeInformation>

    @GET("reviews/{reviewId}")
    fun review(@Path("reviewId") reviewId: Long): Call<ReviewInformation>

    @GET("posts/comments/{postId}")
    fun comments(@Path("postId") postId: Long, @Query("pageNumber") pageNumber: Int?): Call<CommentPageInformation>

    @GET("posts/reviews/{postId}")
    fun reviews(@Path("postId") postId: Long, @Query("pageNumber") pageNumber: Int?): Call<ReviewPageInformation>
}