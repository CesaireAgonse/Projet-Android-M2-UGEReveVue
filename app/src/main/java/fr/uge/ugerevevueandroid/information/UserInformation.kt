package fr.uge.ugerevevueandroid.information

data class UserInformation(
    val username:String,
    val profilePhoto: String,
    val nbFollowed: Int,
    val nbCode:Int,
    val nbReview:Int,
    val nbComments:Int,
    val isFollowed:Boolean
) {}
