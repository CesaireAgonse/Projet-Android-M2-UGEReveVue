package fr.uge.ugerevevueandroid.information

data class CodePageInformation(
    val codes: List<CodeInformation>,
    val pageNumber:Int,
    val maxPageNumber:Int,
    val resultNumber:Int
)
