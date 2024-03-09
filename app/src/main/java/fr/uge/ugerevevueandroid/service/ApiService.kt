package fr.uge.ugerevevueandroid.service
import TokenManager
import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


const val url = "http://10.0.2.2:8080/api/v1/"

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(url)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val authenticationService: AuthenticationService = retrofit.create(AuthenticationService::class.java)
val filterService: FilterService = retrofit.create(FilterService::class.java)

class ApiService(application: Application){
    val manager = TokenManager(application)
    val authToken = manager.getToken("bearer")
    val authInterceptor = authToken?.let { AuthInterceptor(it) }
    val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()
    val retrofitBearer = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUserService() :UserService{
        return retrofitBearer.create(UserService::class.java)
    }
}

