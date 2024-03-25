package fr.uge.ugerevevueandroid.service

import TokenManager
import android.app.Application
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String, private val application: Application) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()
        val response = chain.proceed(modifiedRequest)
        Log.i("response", response.message)
        Log.i("response", response.body.toString())
        Log.i("response", response.code.toString())
        // Check if response is 401 or 403
        if (response.code == 401 || response.code == 403) {


            // Refresh token synchronously or asynchronously based on your requirements
            val newToken = TokenManager(application).getToken("refresh")
                ?.let { allPermitService.refresh(it) }

            Log.i("REFRESH", "REFRESH")
            // If new token is obtained, update the interceptor with the new token
            if (newToken != null) {
                Log.i("REFRESH", "REFRESH2")
                val newModifiedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()

                // Retry the failed request with the new token
                return chain.proceed(newModifiedRequest)
            }
            else {
                Log.i("REFRESH", "REFRESH3")
                TokenManager(application).clearToken("bearer")
                TokenManager(application).clearToken("refresh")
                return chain.proceed(originalRequest)
            }
        }

        return response
    }
}