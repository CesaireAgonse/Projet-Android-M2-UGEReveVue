package fr.uge.ugerevevueandroid.service

import android.app.Application
import fr.uge.ugerevevueandroid.form.CommentForm
import fr.uge.ugerevevueandroid.form.ReviewForm
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun postVoted(application: Application, postId: Long, voteType: String):Long? {
    return withContext(Dispatchers.IO) {
        val response = ApiService(application).authenticateService()
            .postVoted(postId, voteType)
            .execute()
        response.body()
    }
}

suspend fun postCommented(application:Application, postId: Long, commentForm: CommentForm) {
    return withContext(Dispatchers.IO) {
        ApiService(application).authenticateService()
            .postCommented(postId, commentForm)
            .execute()
    }
}

suspend fun postReviewed(application:Application, postId: Long, reviewForm: ReviewForm) {
    return withContext(Dispatchers.IO) {
        ApiService(application).authenticateService()
            .postReviewed(postId, reviewForm)
            .execute()
    }
}

suspend fun comments(postId: Long, pageNumber: Int): CommentPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.comments(postId, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun reviews(postId: Long, pageNumber: Int): ReviewPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.reviews(postId, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
