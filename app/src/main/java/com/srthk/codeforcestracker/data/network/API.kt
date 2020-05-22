package com.srthk.codeforcestracker.data.network

import com.srthk.codeforcestracker.data.db.Contests
import com.srthk.codeforcestracker.util.Constants.CONTESTS
import com.srthk.codeforcestracker.util.Constants.URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface API {
    @GET(CONTESTS)
    suspend fun getAvailableContests(): Response<Contests>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): API {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}