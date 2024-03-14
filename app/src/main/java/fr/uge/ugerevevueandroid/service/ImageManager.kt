package fr.uge.ugerevevueandroid.service

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class ImageManager {

    // interface pour la gestion d'image
    fun byteArrayToImageBitMap(data: ByteArray) : ImageBitmap {
        val imageBitmap = BitmapFactory.decodeByteArray(data, 0, data.size).asImageBitmap()
        return imageBitmap;
    }

    //ImageBitMapToByteArray

    //URIToByteArray
}