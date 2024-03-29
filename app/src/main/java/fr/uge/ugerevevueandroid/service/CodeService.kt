package fr.uge.ugerevevueandroid.service

import android.app.Application
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.FilterInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

suspend fun create(application: Application, title: String, desciption : String, javaFile:  MultipartBody.Part?, unitFile: MultipartBody.Part?){
    return withContext(Dispatchers.IO){
        ApiService(application).authenticateService().create(title,desciption,javaFile,unitFile).execute()
    }
}

suspend fun code(codeId: Long): CodeInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.code(codeId).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun filter(sortBy: String, query: String, pageNumber:Int): FilterInformation?{
    return withContext(Dispatchers.IO) {
        try {
            val response = allPermitService.filter(sortBy, query, pageNumber).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
        catch (e: Exception) {
            null
        }
    }
}

suspend fun codeDeleted(application: Application, postId: Long) {
    return withContext(Dispatchers.IO) {
        try {
            val response = ApiService(application).adminPermitService()
                .codeDeleted(postId)
                .execute()
            response.body()
        }
        catch (e: Exception) {
        }
    }
}