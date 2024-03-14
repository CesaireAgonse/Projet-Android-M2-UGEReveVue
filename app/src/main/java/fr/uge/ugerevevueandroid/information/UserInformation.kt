package fr.uge.ugerevevueandroid.information

data class UserInformation(
    val id:Long,
    val username:String,
    val followed: Set<UserInformation>?,
    val isAdmin:Boolean,
    val profilePhoto:ByteArray = ByteArray(0)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInformation

        if (id != other.id) return false
        if (username != other.username) return false
        if (followed != other.followed) return false
        if (isAdmin != other.isAdmin) return false
        if (!profilePhoto.contentEquals(other.profilePhoto)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + (followed?.hashCode() ?: 0)
        result = 31 * result + isAdmin.hashCode()
        result = 31 * result + profilePhoto.contentHashCode()
        return result
    }
}
