package fr.uge.ugerevevueandroid.page

import android.app.Application
import android.net.Uri
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
import fr.uge.ugerevevueandroid.manager.FileManager
import fr.uge.ugerevevueandroid.service.create

@Composable
fun CreatePage(application:Application, viewModel : MainViewModel){
    var contentTitle by remember { mutableStateOf("") }
    var contentDescription by remember { mutableStateOf("") }
    var selectedJavaFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedUnitFileUri by remember { mutableStateOf<Uri?>(null) }
    var create by remember { mutableStateOf(false) }
    var isJavaFileSelected by remember { mutableStateOf(false) }

    LaunchedEffect(create){
        if(create){
            var javafile = selectedJavaFileUri?.let { FileManager().createMultipartFromUri( it, application.contentResolver, "javaFile") }
            var unitfile = selectedUnitFileUri?.let { FileManager().createMultipartFromUri(it, application.contentResolver, "unitFile") }
            create(application, contentTitle,contentDescription, javafile,unitfile)
            viewModel.changeCurrentPage(Page.HOME)
        }
        create = false
    }
    val javaFilePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedJavaFileUri = it
        }
        if (selectedJavaFileUri != null) {
            isJavaFileSelected = true
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
            },
            enabled = isJavaFileSelected && contentTitle.isNotBlank() && contentDescription.isNotBlank()// Grisé pour rester visible
        ) {
            Text(text = "Post File")
        }
    }
}