package fr.uge.ugerevevueandroid.service


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import fr.uge.ugerevevueandroid.R.layout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


class CameraCaller : Activity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 101
    private lateinit var capturedImageFile: File
    var photo:MultipartBody.Part? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(layout.activity_camera_caller)
        requestPicture()
        capturedImageFile.let { file ->
            if (file.exists()) {
                // Convertir le fichier en RequestBody
                val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

                // Créer un MultipartBody.Part à partir du RequestBody
                photo = MultipartBody.Part.createFormData("image", file.name, requestBody)

                // Utilisez imagePart comme vous le souhaitez, par exemple, envoyez-le via Retrofit
            }
        }
        finish()
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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pictDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            this.javaClass.simpleName
        )
        pictDir.mkdir()
        val timestamp = "image.jpg"
        val dest = File(pictDir, timestamp) // Destination file
        capturedImageFile = dest
        val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", dest)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        try {
            startActivityForResult(intent,IMAGE_CAPTURE_REQUEST_CODE)
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

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_CAPTURE_REQUEST_CODE -> when (resultCode) {
                RESULT_OK -> {
                    capturedImageFile.let { file ->
                        if (file.exists()) {
                            // Convertir le fichier en RequestBody
                            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

                            // Créer un MultipartBody.Part à partir du RequestBody
                            photo = MultipartBody.Part.createFormData("image", file.name, requestBody)

                            // Utilisez imagePart comme vous le souhaitez, par exemple, envoyez-le via Retrofit
                        }
                    }
                }
                RESULT_CANCELED -> Toast.makeText(
                    this,
                    "Capture of image failed",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(this, "Capture of image failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    companion object {
        private const val IMAGE_CAPTURE_REQUEST_CODE = 1
    }
}
