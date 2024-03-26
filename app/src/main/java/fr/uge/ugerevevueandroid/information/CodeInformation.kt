package fr.uge.ugerevevueandroid.information

import java.util.Date

data class CodeInformation(
    val id: Long,
    val userInformation: UserInformation,
    val voteType: String,
    val title: String,
    val description: String,
    val javaContent: String,
    val unitContent: String,
    val testResultsInformation: UnitTestResultInformation,
    val score: Long,
    val date: Date,
    val comments: Int,
    val reviews: Int
)