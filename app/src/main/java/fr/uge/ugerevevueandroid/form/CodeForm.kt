package fr.uge.ugerevevueandroid.form

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part


data class CodeForm( @Part("title")val title: String, @Part("desciption") val desciption : String,@Part("javaFile") val javaFile: MultipartBody.Part,@Part("unitFile") val unitFile:MultipartBody.Part) {
}