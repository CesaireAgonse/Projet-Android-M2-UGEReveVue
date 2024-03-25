package fr.uge.ugerevevueandroid.visual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.information.CodeInformation
import fr.uge.ugerevevueandroid.model.MainViewModel
import fr.uge.ugerevevueandroid.page.Page

@Composable
fun CodePreview(viewModel: MainViewModel, code: CodeInformation, modifier: Modifier = Modifier){
    Surface(
        shadowElevation = 8.dp,
        border = BorderStroke(0.dp, Color.Gray),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        contentColor = Color.Black,
        modifier = modifier.padding(horizontal = 4.dp, vertical = 0.dp)
    )  {
        Column(Modifier.padding(horizontal =8.dp, vertical = 4.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "score: ${code.score}",
                    fontSize = 15.sp,
                    color = Color(0, 0 ,0)
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "reviews: ${code.reviews}",
                    fontSize = 15.sp)
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "comments: ${code.comments}",
                    fontSize = 15.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = code.userInformation.username,
                    modifier = Modifier.clickable {
                        viewModel.changeCurrentPage(Page.USER)
                        viewModel.changeCurrentUserToDisplay(code.userInformation.username)
                    },
                    fontSize = 15.sp
                )
            }
            Row {
                Text(
                    text = code.title,
                    fontSize = 22.sp
                )
            }
            Row {
                Text(
                    text = code.description,
                    fontSize = 15.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify,
                    lineHeight = 17.sp
                )
            }
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${code.date}",
                    fontSize = 12.sp
                )
            }
        }
    }
}