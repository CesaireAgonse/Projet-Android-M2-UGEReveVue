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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInformation

        if (username != other.username) return false
        if (!profilePhoto.contentEquals(other.profilePhoto)) return false
        if (nbFollowed != other.nbFollowed) return false
        if (nbCode != other.nbCode) return false
        if (nbReview != other.nbReview) return false
        if (nbComment != other.nbComment) return false
        return isFollowed == other.isFollowed
    }

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
