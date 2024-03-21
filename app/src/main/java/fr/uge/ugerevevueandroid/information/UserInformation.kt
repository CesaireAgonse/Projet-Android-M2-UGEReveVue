package fr.uge.ugerevevueandroid.information

data class UserInformation(
    val username:String,
    val profilePhoto: String,
    val nbFollowed: Int,
    val nbCode:Int,
    val nbReview:Int,
    val nbComment:Int,
    val isFollowed:Boolean
) {



    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + profilePhoto.hashCode()
        result = 31 * result + nbFollowed
        result = 31 * result + nbCode
        result = 31 * result + nbReview
        result = 31 * result + nbComment
        result = 31 * result + isFollowed.hashCode()
        return result
    }


}
