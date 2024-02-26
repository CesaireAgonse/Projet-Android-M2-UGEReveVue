package fr.uge.ugerevevueandroid.page

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import fr.uge.ugerevevueandroid.model.MainViewModel

@Composable
fun CreatePage(viewModel : MainViewModel){
    var contentTitle by remember { mutableStateOf("") }
    var contentDescription by remember { mutableStateOf("") }
    var selectedJavaFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedUnitFileUri by remember { mutableStateOf<Uri?>(null) }
    var isJavaFileSelected by remember { mutableStateOf(false) }

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
                javaFilePickerLauncher.launch("text/plain")
                if (selectedJavaFileUri != null) {
                    isJavaFileSelected = true
                }
            }) {
                Text(text = "Upload Java File")
            }

            Button(onClick = {
                unitFilePickerLauncher.launch("text/plain")
            }) {
                Text(text = "Upload Unit File")
            }
        }

        Button(
            onClick = {
                // Todo dans le model
                viewModel.changeCurrentPage(Page.HOME)
            },
            enabled = isJavaFileSelected && !contentTitle.equals("") && !contentDescription.equals("")// Grisé pour rester visible
        ) {
            Text(text = "Post File")
        }
    }
}