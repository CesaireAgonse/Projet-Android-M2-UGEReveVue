package fr.uge.ugerevevueandroid.service

import android.app.Application
import fr.uge.ugerevevueandroid.information.ReviewInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun review(reviewId: Long): ReviewInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.review(reviewId).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun reviewDeleted(application: Application, reviewId: Long) {
    return withContext(Dispatchers.IO) {
        var response = ApiService(application).adminPermitService()
            .reviewDeleted(reviewId)
            .execute()
        if (response.isSuccessful){
            response.body()
        }
    }
}