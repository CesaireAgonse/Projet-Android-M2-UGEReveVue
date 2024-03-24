package fr.uge.ugerevevueandroid.information

data class UserPageInformation(
    val users: List<UserInformation>,
    val pageNumber:Int,
    val maxPageNumber:Int,
    val resultNumber:Int
)
