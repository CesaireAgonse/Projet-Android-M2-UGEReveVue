package fr.uge.ugerevevueandroid.information

import java.util.Date

data class ReviewInformation(
    val id: Long,
    val userInformation: UserInformation,
    val title: String,
    val content: String,
    val score: Long,
    val date: Date,
    val comments: List<CommentInformation>,
    val reviews: List<ReviewInformation>
)