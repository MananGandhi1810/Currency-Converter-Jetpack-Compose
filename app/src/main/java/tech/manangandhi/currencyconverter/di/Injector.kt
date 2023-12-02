package tech.manangandhi.currencyconverter.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.manangandhi.currencyconverter.repositories.CurrencyRepository
import tech.manangandhi.currencyconverter.services.CurrencyApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Injector {
    @Singleton
    @Provides
    fun provideInterceptorClient(): OkHttpClient {
        // Your API Key from https://rapidapi.com/fyhao/api/currency-exchange
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "X-RapidAPI-Key",
                        apiKey,
                    )
                    .addHeader(
                        "X-RapidAPI-Host",
                        "currency-exchange.p.rapidapi.com"
                    )
                    .build()
                Log.d("Interceptor", "provideInterceptorClient: $request")
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        interceptorClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://currency-exchange.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(interceptorClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyApi(
        retrofit: Retrofit
    ): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(
        api: CurrencyApi,
    ): CurrencyRepository {
        return CurrencyRepository(
            api
        )
    }
}