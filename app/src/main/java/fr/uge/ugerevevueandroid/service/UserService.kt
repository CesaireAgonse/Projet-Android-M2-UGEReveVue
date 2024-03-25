package fr.uge.ugerevevueandroid.service

import android.app.Application
import android.util.Log
import fr.uge.ugerevevueandroid.form.UpdatePasswordInformation
import fr.uge.ugerevevueandroid.information.CodePageInformation
import fr.uge.ugerevevueandroid.information.CommentPageInformation
import fr.uge.ugerevevueandroid.information.ReviewPageInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.information.UserPageInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Call

suspend fun profile(username: String): UserInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.information(username).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

fun password(application: Application, currentPassword: String, newPassword: String, confirmPassword:String) {
    val call = ApiService(application).authenticateService()
        .password(UpdatePasswordInformation(currentPassword, newPassword))
    call.enqueue(object : retrofit2.Callback<Void> {
        override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
            if (response.isSuccessful){
                Log.i("isSuccessful", "isSuccessful")
            } else {
                Log.i("isNotSuccessful", "isNotSuccessful")
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.i("onFailure", t.message.orEmpty())
        }
    })
}

fun follow(application: Application, username: String) {
    val call = ApiService(application).authenticateService()
        .follow(username)
    call.enqueue(object : retrofit2.Callback<Void> {
        override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
            if (response.isSuccessful){
                Log.i("isSuccessful", "isSuccessful")
            } else {
                Log.i("isNotSuccessful", "isNotSuccessful")
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.i("onFailure", t.message.orEmpty())
        }
    })
}

fun unfollow(application: Application, username: String) {
    val call = ApiService(application).authenticateService()
        .unfollow(username)
    call.enqueue(object : retrofit2.Callback<Void> {
        override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
            if (response.isSuccessful){
                Log.i("isSuccessful", "isSuccessful")
            } else {
                Log.i("isNotSuccessful", "isNotSuccessful")
            }
        }
        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.i("onFailure", t.message.orEmpty())
        }
    })
}

suspend fun codesFromUser(username: String, pageNumber: Int): CodePageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.codesFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun reviewsFromUser(username: String, pageNumber: Int): ReviewPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.reviewsFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun commentsFromUser(username: String, pageNumber: Int): CommentPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.commentsFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun followedsFromUser(username: String, pageNumber: Int): UserPageInformation? {
    return withContext(Dispatchers.IO) {
        val response = allPermitService.followedsFromUser(username, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

suspend fun photo(application: Application, photo: MultipartBody.Part) {
    return withContext(Dispatchers.IO) {
        ApiService(application).authenticateService().photo(photo).execute();
    }
}

suspend fun getAllUsers(application: Application) : UserPageInformation?{
    return withContext(Dispatchers.IO) {
        var response = ApiService(application).adminPermitService()
            .getAllUsers(0)
            .execute()
        if (response.isSuccessful){
            response.body()
        } else {
            null
        }
    }
}

suspend fun userDeleted(application: Application, username: String) {
    return withContext(Dispatchers.IO) {
        var response = ApiService(application).adminPermitService()
            .userDeleted(username)
            .execute()
        if (response.isSuccessful){
            response.body()
        }
    }
}