package fr.uge.ugerevevueandroid.service


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import fr.uge.ugerevevueandroid.MainActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class CameraCaller : Activity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 101
    private lateinit var capturedImageFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CameraCaller", "onCreate")
        requestPicture()

    }

    fun startCameraActivity() {
        // Vérifier si la permission de la caméra est déjà accordée
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // La permission est déjà accordée, donc démarrer l'activité de la caméra
            launchCamera()
        } else {
            // La permission n'est pas accordée, donc demander à l'utilisateur
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun launchCamera() {
        Log.d("CameraCaller", "launchCamera")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pictDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            this.javaClass.simpleName
        )
        pictDir.mkdir()
        val timestamp = "image.jpg"
        val dest = File(pictDir, timestamp) // Destination file
        capturedImageFile = dest
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
                // La permission de la caméra a été accordée, donc démarrer l'activité de la caméra
                launchCamera() // true ou false selon votre besoin
            } else {
                // La permission de la caméra a été refusée, vous pouvez informer l'utilisateur ici
            }
        }
    }

    fun requestPicture() {
        startCameraActivity()
    }


    companion object {
        private const val IMAGE_CAPTURE_REQUEST_CODE = 1
    }

    // Mettez à jour la méthode onActivityResult pour appeler la lambda de rappel
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("CameraCaller", "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")

        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d("CameraCaller", "Image captured successfully")
            // Notify the listener if set

            val d = Intent().apply {
                putExtra("photo", capturedImageFile.path) // Remplacez "key" par la clé pour les données que vous souhaitez retourner
            }
            setResult(Activity.RESULT_OK, d)

        }else{
            Log.e("CameraCaller", "Image capture failed")
        }
        finish()
    }
}


