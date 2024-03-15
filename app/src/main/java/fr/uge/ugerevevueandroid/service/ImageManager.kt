package fr.uge.ugerevevueandroid.service

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.IOException
import java.io.InputStream

class ImageManager {

    // interface pour la gestion d'image
    fun byteArrayToImageBitMap(data: ByteArray) : ImageBitmap {
        val imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.size).asImageBitmap()
        return imageBitmap;
    }

    //ImageBitMapToByteArray

    /*
    fun URIToByteArray(uri: Uri): ByteArray {
        val contentResolver = ContentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        return inputStream?.use { it.readBytes() } ?: throw IOException("Unable to read image from URI")
    }
    */
}