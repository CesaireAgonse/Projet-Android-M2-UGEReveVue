package fr.uge.ugerevevueandroid.page

import TokenManager
import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.FilterInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.allPermitService
import fr.uge.ugerevevueandroid.service.filter
import fr.uge.ugerevevueandroid.visual.CodePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


val HOME_COLOR= Color(241, 241, 241, 255)

@Composable
fun HomePage(application: Application, viewModel: MainViewModel){
    var sortBy by remember {mutableStateOf(viewModel.currentSortBy)}
    var query by remember {mutableStateOf(viewModel.currentQuery)}
    var pageNumber by remember { mutableIntStateOf(0) }
    var maxPageNumber by remember { mutableIntStateOf(0) }
    var resultNumber by remember { mutableIntStateOf(0) }

    var posts:FilterInformation? by remember {mutableStateOf( null)}
    LaunchedEffect(true, posts, maxPageNumber, pageNumber, query, sortBy, viewModel.currentQuery, viewModel.currentSortBy, viewModel.triggerReloadPage) {
        posts = filter(viewModel.currentSortBy, viewModel.currentQuery, pageNumber)
        if (posts != null){
            sortBy = posts!!.sortBy
            query = posts!!.q
            pageNumber = posts!!.pageNumber
            maxPageNumber = posts!!.maxPageNumber
            resultNumber = posts!!.resultNumber
        }
    }
    val scrollState = rememberScrollState()
    if (posts != null){
        Scaffold(
            topBar = {
                FistRow(viewModel, resultNumber, application)
            },
            floatingActionButton = {
                if (TokenManager(application).getAuth() != null){
                    FloatingActionButton(onClick = { viewModel.changeCurrentPage(Page.CREATE) }, contentColor = Color(R.color.button_color_2)) {
                        Icon(Icons.Filled.Add, contentDescription = "Add a new Post")
                    }
                }
            }
        ) {
                innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .background(HOME_COLOR),
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
                Row (Modifier.background(HOME_COLOR)){
                    if(pageNumber >= 1){
                        Button(onClick = {pageNumber--},
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            shape = RectangleShape,
                        ) {
                            Text(text = "Previous")
                        }
                    }
                    if(pageNumber < maxPageNumber) {
                        Button(onClick = { pageNumber++ },
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                            ),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            shape = RectangleShape,

                        ) {
                            Text(text = "Next")
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun FistRow(viewModel: MainViewModel, numberResult: Int, application: Application){

    Column(Modifier.background(HOME_COLOR)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Codes",
                modifier = Modifier.weight(1f),
                fontSize = 30.sp
            )

            // Bouton Admin
            if (TokenManager(application).getAuth()?.role.equals("ADMIN")) {
                Button(onClick = {viewModel.changeCurrentPage(Page.ADMIN) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(52, 152, 219),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(4.dp),
                    shape = CircleShape) {
                    Text(text = "Admin page")
                }
            }

        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var expanded by remember { mutableStateOf(false) }
            if (viewModel.currentSortBy == ""){
                Text(
                    text = "$numberResult results" ,
                    modifier = Modifier.weight(1f)
                )
            }
            else {
                Text(
                    text = "$numberResult results by " +  viewModel.currentSortBy,
                    modifier = Modifier.weight(1f)
                )
            }
            Button(onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.padding(4.dp),
                shape = CircleShape

                ) {
                Text(
                    text = "Sort by",
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Newest")},
                        onClick = { expanded = false;
                            if (viewModel.currentSortBy == "newest"){
                                viewModel.changeCurrentSortBy("")
                            }
                            else {
                                viewModel.changeCurrentSortBy("newest")
                            }

                        })
                    DropdownMenuItem(
                        text = {
                            Text(text = "Relevance")},
                        onClick = { expanded = false;
                            if (viewModel.currentSortBy == "relevance"){
                                viewModel.changeCurrentSortBy("")
                            }
                            else {
                                viewModel.changeCurrentSortBy("relevance")
                            }

                        }
                    )
                }
            }
        }
    }
}

