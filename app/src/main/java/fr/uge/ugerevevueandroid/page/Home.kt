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
import androidx.compose.runtime.getValue
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
import fr.uge.ugerevevueandroid.information.CommentInformation
import fr.uge.ugerevevueandroid.information.ReviewInformation
import fr.uge.ugerevevueandroid.information.SimpleUserInformation
import java.util.Date

fun loadPosts() : MutableList<CodeInformation> {
    val posts = mutableStateOf(mutableListOf<CodeInformation>())
    var comments = HashSet<CommentInformation>();
    var reviews = HashSet<ReviewInformation>();
    var follows = HashSet<SimpleUserInformation>();
    Log.i("size : ", follows.size.toString())
    var admin = SimpleUserInformation(1, "admin", follows,true)
    var czer = SimpleUserInformation(2, "czer", HashSet<SimpleUserInformation>(),false)
    admin.followed.add(czer)
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

@Composable
fun HomePage(redirection : (Page) -> Unit, setUser : (SimpleUserInformation) -> Unit){
    var posts = loadPosts()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            FistRow(isAdmin = true, 42)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { redirection(Page.CREATE) }, contentColor = Color(R.color.button_color_2)) {
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
            posts.forEach{
                Post(code = it,
                    modifier = Modifier.clickable { redirection(Page.CODE) },
                    redirection,
                    setUser)
            }

        }
    }


}

@Composable
fun FistRow(isAdmin : Boolean, numberResult: Int){

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
            if (isAdmin) {
                Button(onClick = { /*TODO*/ }) {
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
fun Post(code: CodeInformation, modifier: Modifier = Modifier,
         redirection : (Page) -> Unit,
         setUser : (SimpleUserInformation) -> Unit){
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
            Text(text = "reviews: ${code.review.size}",
                fontSize = 10.sp)
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(text = "comments: ${code.comments.size}",
                fontSize = 10.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${code.userInformation.username}",
                modifier = Modifier.clickable { /*User(user = code.userInformation)*/
                redirection(Page.USER)
                setUser(code.userInformation)
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