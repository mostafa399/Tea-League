package com.mstfahlal.tealeague.data.data_sources.remote

import com.mstfahlal.tealeague.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface FootballApiService {
    @GET("competitions/")
    @Headers("X-Auth-Token: ${BuildConfig.TOKEN}")
    suspend fun getCompetitions(): Response<RemoteCompetitions>

}