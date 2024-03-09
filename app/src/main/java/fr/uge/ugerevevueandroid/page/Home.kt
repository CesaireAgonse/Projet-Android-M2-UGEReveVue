package fr.uge.ugerevevueandroid.page

import android.app.Application
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
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.form.LoginForm
import fr.uge.ugerevevueandroid.form.TokenForm
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.information.CommentInformation
import fr.uge.ugerevevueandroid.information.FilterInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import fr.uge.ugerevevueandroid.information.UserInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.service.ApiService
import fr.uge.ugerevevueandroid.service.authenticationService
import fr.uge.ugerevevueandroid.service.filterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.http.Query
import java.util.Date

fun loadPosts() : MutableList<CodeInformation> {
    val posts = mutableStateOf(mutableListOf<CodeInformation>())
    var comments = HashSet<CommentInformation>();
    var reviews = HashSet<ReviewInformation>();
    var follows = HashSet<SimpleUserInformation>();
    Log.i("size : ", follows.size.toString())
    var admin = UserInformation(1, "admin", null,true)
    var czer = UserInformation(2, "czer", null, false)
    //admin.followed.add(czer)

    comments.add(CommentInformation(666, czer, "Trop bien ce code !", null ,Date()))
    reviews.add(
        ReviewInformation(999,
            czer,
            "askip ya un titre",
            "effectivement ceci est un bon code j'approuve",
            42,
            Date(),
            mutableListOf(),
            mutableListOf())
    )

    var firstPost = CodeInformation(
        0,
        "Ceci est le titre du premier Post",
        "Et cela la description du même post, Lorem ipsum dolor sit amet, consectetur " +
                "adipiscing elit. Proin et mauris feugiat, ullamcorper odio non, hendrerit mauris." +
                " Pellentesque congue tincidunt ex nec auctor. Morbi suscipit et neque sed blandit." +
                " Praesent eu enim id lacus placerat tempor. Quisque arcu elit, sodales et hendrerit" +
                " eget, efficitur in massa. Proin consequat a magna tristique posuere. Nunc tristique" +
                " dui at ante posuere, eu cursus magna finibus. Integer suscipit sagittis urna," +
                " vitae efficitur ipsum molestie ac. Nunc hendrerit velit arcu, ut finibus turpis" +
                " congue ut.",
        "",
        "",
        666,
        Date(),
        admin,
        comments,
        reviews,
    )

    var secondPost = CodeInformation(
        0,
        "Et cela le titre d'un autre Post",
        "Avec une description minuscule.",
        "",
        "",
        666,
        Date(),
        czer,
        comments,
        reviews,
    )
    posts.value.add(firstPost)
    posts.value.add(secondPost)
    posts.value.add(firstPost)
    posts.value.add(firstPost)
    posts.value.add(secondPost)
    posts.value.add(firstPost)
    posts.value.add(firstPost)

    return posts.value
}

suspend fun filter(sortBy: String, query: String, pageNumber:Int):FilterInformation ?{
    return withContext(Dispatchers.IO) {
        val response = filterService.filter(sortBy, query, pageNumber).execute()
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}




@Composable
fun HomePage(viewModel: MainViewModel){
    var sortBy by remember {mutableStateOf("")}
    var query by remember {mutableStateOf("")}
    var pageNumber by remember { mutableIntStateOf(0) }


    var posts:FilterInformation? by remember {mutableStateOf( null)}
    LaunchedEffect(true, posts, pageNumber, query, sortBy) {
        posts = filter("newest", "", 0)
        if (posts != null){
            sortBy = posts!!.sortBy
            query = posts!!.q
            pageNumber = posts!!.pageNumber
        }
    }
    val scrollState = rememberScrollState()
    if (posts != null){
        Log.i("test", posts!!.codes.size.toString())
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
                    Post(viewModel = viewModel,
                        code = it,
                        modifier = Modifier.clickable {
                            viewModel.changeCurrentCodeToDisplay(it)
                            viewModel.changeCurrentPage(Page.CODE)
                        })
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
            if (viewModel.adminAccess()) {
                Button(onClick = {viewModel.changeCurrentPage(Page.ADMIN) }) {
                    Text(text = "Admin page")
                }
            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nombre de résultats et menu déroulant
            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember { mutableStateOf("Newest") }

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
                                modifier = Modifier.clickable{ expanded = false})
                        },
                        onClick = { /*TODO*/ })
                    DropdownMenuItem(
                        text = {
                            Text(text = "Revelance",
                                modifier = Modifier.clickable{ expanded = false})
                        },
                        onClick = { /*TODO*/ })
                }
            }

        }
    }



}

@Composable
fun Post(viewModel: MainViewModel, code: CodeInformation, modifier: Modifier = Modifier){
    Column (
        modifier = modifier.padding(2.dp)
        // mettre un petit background et delimiter chaque component
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "score: ${code.score}",
                fontSize = 10.sp)
            Spacer(modifier = Modifier.padding(start = 4.dp))
//            Text(text = "reviews: ${code.review.size}",
//                fontSize = 10.sp)
//            Spacer(modifier = Modifier.padding(start = 4.dp))
//            Text(text = "comments: ${code.comments.size}",
//                fontSize = 10.sp)
//            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${code.userInformation.username}",
                modifier = Modifier.clickable { /*User(user = code.userInformation)*/
                    viewModel.changeCurrentPage(Page.USER)
                    viewModel.changeCurrentUserToDisplay(code.userInformation)
                }
            )
        }
        Row {
            Text(
                text = "${code.title}",
                fontSize = 20.sp
            )
        }
        Row {
            Text(
                text = "${code.description}",
                fontSize = 10.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
                lineHeight = 15.sp
            )
        }
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${code.date}",
                fontSize = 10.sp
            )
        }
    }


}