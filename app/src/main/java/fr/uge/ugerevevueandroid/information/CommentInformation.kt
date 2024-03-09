package fr.uge.ugerevevueandroid.information

import java.util.Date

data class CommentInformation(
    val id: Long,
    val userInformation: UserInformation,
    val content: String,
    val codeSelection: String,
    val date: Date
)