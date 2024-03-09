package fr.uge.ugerevevueandroid.information

data class FilterInformation(
    val codes :List<CodeInformation>,
    val sortBy:String, val q:String,
    val pageNumber: Int
)