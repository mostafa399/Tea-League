package com.mstfahlal.tealeague.data.data_sources.remote

import android.util.Log
import com.mstfahlal.tealeague.utils.Resource
import javax.inject.Inject

class CompetitionRemoteDataImp @Inject constructor(
    private val apiService: FootballApiService
) : ICompetitionRemoteData {
    override suspend fun getCompetition(): Resource<RemoteCompetitions> {
        return try {
            val competitionsResponse = apiService.getCompetitions()
            Log.d("CompetitionRemoteDataImp", "Response code: ${competitionsResponse.code()}")
            Log.d("CompetitionRemoteDataImp", "Response headers: ${competitionsResponse.headers()}")

            if (competitionsResponse.isSuccessful) {
                competitionsResponse.body()?.let {
                    Log.d("CompetitionRemoteDataImp", "Received ${it.competitions?.size} competitions")
                    Resource.Success(it)
                } ?: run {
                    Log.d("CompetitionRemoteDataImp", "Response body is null")
                    Resource.Success(RemoteCompetitions(emptyList(), null, null))
                }
            } else {
                val errorBody = competitionsResponse.errorBody()?.string()
                Log.e("CompetitionRemoteDataImp", "Error response: $errorBody")
                Resource.Error(competitionsResponse.message() ?: "Unknown error")
            }
        } catch (e: Exception) {
            Log.e("CompetitionRemoteDataImp", "Exception: ${e.message}", e)
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}