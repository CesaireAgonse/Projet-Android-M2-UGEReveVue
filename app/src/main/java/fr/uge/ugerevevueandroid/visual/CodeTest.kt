package fr.uge.ugerevevueandroid.visual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.uge.ugerevevueandroid.information.CodeInformation

@Composable
fun CodeTest(codeInformation : CodeInformation){
    Surface(
        shadowElevation = 8.dp,
        border = BorderStroke(0.dp, Color.Gray),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        contentColor = Color.Black,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(Modifier.padding(5.dp)) {
            Text(text = codeInformation.unitContent)
            Text(text = "Total tests: " + codeInformation.testResultsInformation.testsTotalCount)
            Text(text = "Total succeed tests: " + codeInformation.testResultsInformation.testsSucceededCount)
            Text(text = "Total failed tests: " + codeInformation.testResultsInformation.testsFailedCount)
            Text(text = "Time: " + codeInformation.testResultsInformation.testsTotalTime + " ms")
            Text(text = "Failure: " + codeInformation.testResultsInformation.failures)
        }
    }
}