package fr.uge.ugerevevueandroid.visual

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.ugerevevueandroid.R
import fr.uge.ugerevevueandroid.information.CodeInformation

@Composable
fun Code(code : CodeInformation){
    var voteButtonClicked : Boolean by remember { mutableStateOf(false) }
    Text(
        text = "${code.title}",
        fontSize = 30.sp
    )
    Row {
        Text(
            text = "from : ${code.userInformation.username}",
            modifier = Modifier.clickable {  }
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "${code.date}")
    }
    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp))

    Row {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ voteButtonClicked = !voteButtonClicked},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = colorResource(id = R.color.button_color_2)
                ),
                shape = CircleShape
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "UpVote")
            }
            Text(text = "${code.score}")
            Button(onClick = { /*TODO*/ voteButtonClicked = !voteButtonClicked},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_color),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = colorResource(id = R.color.button_color_2)
                ),
                shape = CircleShape
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "DownVote")
            }
        }
        Column {
            Text(text = code.description,
                textAlign = TextAlign.Justify)

            Text(text = code.javaContent)
            if (code.unitContent != null){
                Text(text = code.unitContent)
            }
        }
    }
}