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
        if (response.code == 401 || response.code == 403 || response.code == 500) {
            val refreshToken = TokenManager(application).getToken("refresh")
            response.close();
            if (refreshToken != null) {
                val token = refresh(refreshToken)
                if (token != null){
                    TokenManager(application).saveToken("bearer", token.bearer)
                    TokenManager(application).saveToken("refresh", token.refresh)
                    val newModifiedRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${token.bearer}")
                        .build()
                    return chain.proceed(newModifiedRequest)
                }
                TokenManager(application).clearToken("bearer")
                TokenManager(application).clearToken("refresh")
                return chain.proceed(originalRequest)

            }
        }

        return response
    }
}