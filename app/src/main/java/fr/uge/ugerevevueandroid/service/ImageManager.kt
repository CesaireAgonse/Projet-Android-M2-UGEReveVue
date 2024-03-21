package fr.uge.ugerevevueandroid.service


import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap

class ImageManager {

    // interface pour la gestion d'image
    fun base64ToImageBitMap(data: String) : ImageBitmap {
        val decodedBytes = Base64.decode(data, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        return bitmap.asImageBitmap();
    }

    fun createMultipartFromUri(uri: Uri, contentResolver: ContentResolver): MultipartBody.Part? {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val file = File.createTempFile("temp", null)
                val outputStream = FileOutputStream(file)
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                val mediaType = contentResolver.getType(uri)?.toMediaTypeOrNull()
                val requestBody = file.asRequestBody(mediaType)
                return MultipartBody.Part.createFormData("photo", file.name, requestBody)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}