package fr.uge.ugerevevueandroid.activity

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class CameraCaller : Activity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 101
    private lateinit var capturedImageFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CameraCaller", "onCreate")
        requestPicture()

    }

    fun startCameraActivity() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCamera()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun launchCamera() {
        Log.d("CameraCaller", "launchCamera")

        // Create the directory if it doesn't exist
        val pictDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            this.javaClass.simpleName
        )
        pictDir.mkdir()

        // Create the file to save the image
        val timestamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".jpg"
        val dest = File(pictDir, timestamp)
        capturedImageFile = dest

        // Get URI for the file using FileProvider
        val uri = FileProvider.getUriForFile(this, "fr.uge.ugerevevueandroid.fileprovider", capturedImageFile)

        // Grant temporary read permission to the content URI
        grantUriPermission("com.android.camera", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

        // Launch the camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        try {
            startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            Log.e(javaClass.name, "Cannot start the activity due to an exception", e)
            Toast.makeText(this, "An exception encountered: $e", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera() // true ou false selon votre besoin
            }
        }
    }

    fun requestPicture() {
        startCameraActivity()
    }

    companion object {
        private const val IMAGE_CAPTURE_REQUEST_CODE = 1
    }

    private fun saveImageToFile(bitmap: Bitmap, file: File) {
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Get the URI for the file using FileProvider
            val uri = FileProvider.getUriForFile(this, "fr.uge.ugerevevueandroid.fileprovider", file)

            // Grant temporary read permission to the content URI
            grantUriPermission("fr.uge.ugerevevueandroid", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("Save Image", "Error saving image: ${e.message}")
            Toast.makeText(this, "Error saving image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("CameraCaller", "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d("CameraCaller", "Image captured successfully")
            // Notify the listener if set

            val d = Intent().apply {
                putExtra("photo", capturedImageFile.path) // Remplacez "key" par la clé pour les données que vous souhaitez retourner
            }

            val imageBitmap = data?.extras?.get("data") as Bitmap?
            if (imageBitmap != null) {
                saveImageToFile(imageBitmap, capturedImageFile)
            }

            Log.i("LE D", d.extras.toString())
            setResult(Activity.RESULT_OK, d)
        }else{
            Log.e("CameraCaller", "Image capture failed")
        }
        finish()
    }
}


