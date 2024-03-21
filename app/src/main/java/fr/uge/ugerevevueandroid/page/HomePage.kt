package fr.uge.ugerevevueandroid.page

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.FilterInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.allPermitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun filter(sortBy: String, query: String, pageNumber:Int):FilterInformation ?{
    return withContext(Dispatchers.IO) {
        val response = allPermitService.filter(sortBy, query, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

@Composable
fun HomePage(viewModel: MainViewModel){
    var sortBy by remember {mutableStateOf(viewModel.currentSortBy)}
    var query by remember {mutableStateOf(viewModel.currentQuery)}
    var pageNumber by remember { mutableIntStateOf(0) }
    var maxPageNumber by remember { mutableIntStateOf(0) }

    var posts:FilterInformation? by remember {mutableStateOf( null)}
    LaunchedEffect(true, posts, maxPageNumber, pageNumber, query, sortBy, viewModel.currentQuery, viewModel.currentSortBy) {
        posts = filter(viewModel.currentSortBy, viewModel.currentQuery, pageNumber)
        if (posts != null){
            sortBy = posts!!.sortBy
            query = posts!!.q
            pageNumber = posts!!.pageNumber
            maxPageNumber = posts!!.maxPageNumber
        }
        Log.i("RIKUDAX", viewModel.currentSortBy)
    }
    val scrollState = rememberScrollState()
    if (posts != null){
        Scaffold(
            topBar = {
                FistRow(viewModel, posts!!.codes.size)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { viewModel.changeCurrentPage(Page.CREATE) }, contentColor = Color(R.color.button_color_2)) {
                    Icon(Icons.Filled.Add, contentDescription = "Add a new Post")
                }
            }
        ) {
                innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                posts!!.codes.forEach{
                    CodePreview(viewModel = viewModel,
                        code = it,
                        modifier = Modifier.clickable {
                            viewModel.changeCurrentCodeToDisplay(it.id)
                            viewModel.changeCurrentPage(Page.CODE)
                        })
                }
                Row{
                    if(pageNumber >= 1){
                        Button(onClick = {pageNumber--}) {
                            Text(text = "Previous")
                        }
                    }
                    if(pageNumber < maxPageNumber) {
                        Button(onClick = { pageNumber++ }) {
                            Text(text = "Next")
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun FistRow(viewModel: MainViewModel, numberResult: Int){

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Titre
            Text(
                text = "All Codes",
                modifier = Modifier.weight(1f),
                fontSize = 30.sp
            )

            // Bouton Admin
//            if (viewModel.adminAccess()) {
//                Button(onClick = {viewModel.changeCurrentPage(Page.ADMIN) }) {
//                    Text(text = "Admin page")
//                }
//            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nombre de résultats et menu déroulant
            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember { mutableStateOf("newest") }

            Text(
                text = "${numberResult} results",
                modifier = Modifier.weight(1f)
            )

            Button(onClick = { expanded = true }) {
                Text(
                    text = "Sort by",
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Newest",
                                modifier = Modifier.clickable{ expanded = false; viewModel.changeCurrentSortBy("relevance") })
                        },
                        onClick = { expanded = false; viewModel.changeCurrentSortBy("newest") })
                    DropdownMenuItem(
                        text = {
                            Text(text = "Relevance",
                                modifier = Modifier.clickable{ expanded = false; viewModel.changeCurrentSortBy("relevance") })
                        },
                        onClick = { expanded = false; viewModel.changeCurrentSortBy("relevance") }
                    )
                }
            }
        }
    }
}

