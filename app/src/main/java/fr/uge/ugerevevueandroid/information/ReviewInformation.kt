package fr.uge.ugerevevueandroid.information

import java.util.Date

data class ReviewInformation(
    val id: Long,
    val userInformation: UserInformation,
    val title: String,
    val content: List<CommentInformation>,
    val score: Long,
    val date: Date,
    val comments: Int,
    val reviews: Int
)