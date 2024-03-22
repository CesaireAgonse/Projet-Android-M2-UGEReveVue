package fr.uge.ugerevevueandroid.information

data class CommentPageInformation(
    val comments: List<CommentInformation>,
    val pageNumber:Int,
    val maxPageNumber:Int,
    val resultNumber:Int
)
