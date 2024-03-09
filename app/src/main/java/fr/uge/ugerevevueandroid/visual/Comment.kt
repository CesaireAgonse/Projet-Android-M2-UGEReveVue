package fr.uge.ugerevevueandroid.visual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.uge.ugerevevueandroid.information.CommentInformation

@Composable
fun Comment(comment: CommentInformation){
    Surface(
        shadowElevation = 8.dp,
        border = BorderStroke(0.dp, Color.Gray),
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        contentColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = comment.userInformation.username,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /*TODO*/}
            )
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp))
            Text(text = comment.content)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "${comment.date}")
            }
        }
    }
}