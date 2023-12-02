package tech.manangandhi.currencyconverter.services

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {
    @GET("exchange")
    suspend fun exchange(
        @Query("from") from: String,
        @Query("to") to: String,
    ): Double
}
