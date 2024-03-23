package fr.uge.ugerevevueandroid.service


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import fr.uge.ugerevevueandroid.R.layout



    class CameraCaller : Activity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(layout.activity_camera_caller)
        }

        fun startCameraActivity(video: Boolean) {
            val intent =
                Intent(if (video) MediaStore.ACTION_VIDEO_CAPTURE else MediaStore.ACTION_IMAGE_CAPTURE)
            // We can also use MediaStore.ACTION_IMAGE_CAPTURE_SECURE to capture an image in lock mode
            // To capture a video, we use MediaStore.ACTION_VIDEO_CAPTURE
            val pictDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                this.javaClass.simpleName
            )
            pictDir.mkdir()
            val timestamp: String =
                if (video) "0.mp4" else "0.jpg"
            val dest = File(pictDir, timestamp) // Destination file
            intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(dest)
            ) // Specify the destination file
            // Now, we start the picture capture activity
            val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", dest)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            try {
                startActivityForResult(
                    intent,
                    if (video) VIDEO_CAPTURE_REQUEST_CODE else IMAGE_CAPTURE_REQUEST_CODE
                )
            } catch (e: ActivityNotFoundException) {
                Log.e(javaClass.name, "Cannot start the activity due to an exception", e)
                Toast.makeText(this, "An exception encountered: $e", Toast.LENGTH_SHORT).show()
            }
        }

        fun requestPicture(v: View?) {
            startCameraActivity(false)
        }

        fun requestVideo(v: View?) {
            startCameraActivity(true)
        }

        public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            when (requestCode) {
                IMAGE_CAPTURE_REQUEST_CODE -> when (resultCode) {
                    RESULT_OK -> Toast.makeText(
                        this,
                        "Image saved to " + data.data,
                        Toast.LENGTH_LONG
                    ).show()

                    RESULT_CANCELED -> Toast.makeText(
                        this,
                        "Capture of image failed",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> Toast.makeText(this, "Capture of image failed", Toast.LENGTH_SHORT)
                        .show()
                }

                VIDEO_CAPTURE_REQUEST_CODE -> when (resultCode) {
                    RESULT_OK -> Toast.makeText(
                        this,
                        "Video saved to " + data.data,
                        Toast.LENGTH_LONG
                    ).show()

                    RESULT_CANCELED -> Toast.makeText(
                        this,
                        "Capture of video failed",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> Toast.makeText(this, "Capture of video failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        companion object {
            private const val IMAGE_CAPTURE_REQUEST_CODE = 1
            private const val VIDEO_CAPTURE_REQUEST_CODE = 2
        }
    }
