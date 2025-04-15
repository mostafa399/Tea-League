package com.mstfahlal.tealeague.data.repository

import android.content.Context
import com.mstfahlal.tealeague.data.data_sources.local.AppDatabase
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetition
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetitions
import com.mstfahlal.tealeague.data.data_sources.remote.ICompetitionRemoteData
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCompetitions
import com.mstfahlal.tealeague.data.mappers.toDomainCompetitions
import com.mstfahlal.tealeague.data.mappers.toLocalCompetition
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.domain.repository.ICompetitionRepository
import com.mstfahlal.tealeague.utils.Resource
import javax.inject.Inject

class CompetitionRepositoryImp @Inject constructor(
    private val  competitionRemoteDataSource : ICompetitionRemoteData,
    private val competitionLocalDataSource : AppDatabase,
): ICompetitionRepository {
    override suspend fun getCompetition(context: Context): Resource<DomainCompetitions> {
        return if (connectivity(context)) {
            val result = getOnlineCompetition()
            if (result is Resource.Success) {
                result
            } else {
                // fallback to local cache if online fetch failed
                getOfflineCompetition()
            }
        } else {
            getOfflineCompetition()
        }
    }

    suspend fun getOnlineCompetition(): Resource<DomainCompetitions> {
        val dataFromRemote = getFromRemote()
        if (dataFromRemote.data?.competitions!=null){
            saveToLocal(dataFromRemote.data.competitions.toLocalCompetition())
            return Resource.Success(dataFromRemote.data.toDomainCompetitions())
        }else {
            return Resource.Error(" repoImp : ${dataFromRemote.message.toString()}")
        }
    }
    private fun getOfflineCompetition():Resource<DomainCompetitions>{
        val data = competitionLocalDataSource.CompetitionDao().getAll()
        if (data!=null){
            return Resource.Success(LocalCompetitions(data).toDomainCompetitions())
        } else {
            return Resource.Error("Database is empty!")
        }
    }


    override suspend fun getFromLocal(): LocalCompetitions {
        val local = competitionLocalDataSource.CompetitionDao().getAll()
        return LocalCompetitions(local)
    }

    override suspend fun getFromRemote(): Resource<RemoteCompetitions> {
        val dataFromRemote = competitionRemoteDataSource.getCompetition()
        if (dataFromRemote.data == null){
            return Resource.Error(dataFromRemote.message.toString())
        } else {
            return Resource.Success(dataFromRemote.data)
        }
    }

    override suspend fun saveToLocal(data: List<LocalCompetition>) {
        competitionLocalDataSource.CompetitionDao().insert(data)
    }
    @Suppress("DEPRECATION")
    fun connectivity(context: Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}