package fr.uge.ugerevevueandroid.page

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ImageManager
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


fun createMultipartFromUri(uri: Uri, contentResolver: ContentResolver, name:String): MultipartBody.Part? {
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
            return MultipartBody.Part.createFormData(name, file.name, requestBody)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

@Composable
fun CreatePage(viewModel : MainViewModel,application:Application){
    var contentTitle by remember { mutableStateOf("") }
    var contentDescription by remember { mutableStateOf("") }
    var selectedJavaFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedUnitFileUri by remember { mutableStateOf<Uri?>(null) }
    var isJavaFileSelected by remember { mutableStateOf(false) }
    var create by remember { mutableStateOf(false) }
    LaunchedEffect(create){
        if(create){
            var javafile = selectedJavaFileUri?.let { createMultipartFromUri( it, application.contentResolver, "javaFile") }
            var unitfile = selectedUnitFileUri?.let { createMultipartFromUri(it, application.contentResolver, "unitFile") }
            if (javafile != null && unitfile != null) {
                create(contentTitle,contentDescription, javafile,unitfile,application)
            }
        }
        create = false
    }
    val javaFilePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedJavaFileUri = it
        }
    }

    val unitFilePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedUnitFileUri = it
        }
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Post your code",
            //modifier = Modifier.weight(1f),
            fontSize = 30.sp
        )

        OutlinedTextField(
            value = contentTitle,
            onValueChange = { contentTitle = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Your title here", color = Color.LightGray) }
        )
        OutlinedTextField(
            value = contentDescription,
            onValueChange = { contentDescription = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            label = { Text(text = "Your description here", color = Color.LightGray) }
        )

        Row {
            Button(onClick = {
                javaFilePickerLauncher.launch("*/*");
                if (selectedJavaFileUri != null) {
                    isJavaFileSelected = true
                }
            }) {
                Text(text = "Upload Java File")
            }

            Button(onClick = {
                unitFilePickerLauncher.launch("*/*");
            }) {
                Text(text = "Upload Unit File")
            }
        }

        Button(
            onClick = {
                create = true
                //viewModel.changeCurrentPage(Page.HOME)
            },
            enabled = isJavaFileSelected && !contentTitle.equals("") && !contentDescription.equals("")// Grisé pour rester visible
        ) {
            Text(text = "Post File")
        }
    }
}