package fr.uge.ugerevevueandroid.form

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part


data class CodeForm(val title: String,val desciption : String,@Part val javaFile: MultipartBody.Part,@Part val unitFile:MultipartBody.Part) {
}