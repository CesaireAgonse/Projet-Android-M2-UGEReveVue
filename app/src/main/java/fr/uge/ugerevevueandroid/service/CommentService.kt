package fr.uge.ugerevevueandroid.service

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun commentDeleted(application: Application, commentId: Long) {
    return withContext(Dispatchers.IO) {
        try {
            var response = ApiService(application).adminPermitService()
                .commentDeleted(commentId)
                .execute()
            if (response.isSuccessful){
                response.body()
            }
        }
        catch (e: Exception) {
        }
    }
}