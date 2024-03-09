package fr.uge.ugerevevueandroid.service

import fr.uge.ugerevevueandroid.information.FilterInformation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilterService {
    @GET("codes/filter")
    fun filter(
        @Query("sortBy") sortBy: String?,
        @Query("q") query: String?,
        @Query("pageNumber") pageNumber: Int?
    ): Call<FilterInformation>
}