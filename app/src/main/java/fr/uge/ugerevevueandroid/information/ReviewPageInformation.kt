package fr.uge.ugerevevueandroid.information

data class ReviewPageInformation(
    val reviews: List<ReviewInformation>,
    val pageNumber:Int,
    val maxPageNumber:Int,
    val resultNumber:Int,
)
