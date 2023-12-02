package tech.manangandhi.currencyconverter.repositories

import tech.manangandhi.currencyconverter.services.CurrencyApi
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    val api: CurrencyApi
) {
    suspend fun exchange(
        from: String,
        to: String,
    ): Double {
        return api.exchange(
            from,
            to,
        )
    }
}