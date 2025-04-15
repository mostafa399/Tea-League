package com.mstfahlal.tealeague.data.repository

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompetitionRepositoryImp @Inject constructor(
    private val competitionRemoteDataSource: ICompetitionRemoteData,
    private val competitionLocalDataSource: AppDatabase
) : ICompetitionRepository {
    private var hasInitialRemoteLoad = false

    override suspend fun getCompetition(forceRefresh: Boolean): Resource<DomainCompetitions> {
        return if (forceRefresh || !hasInitialRemoteLoad) {
            getRemoteAndCache().also {
                if (it is Resource.Success) {
                    hasInitialRemoteLoad = true
                }
            }
        } else {
            getLocalData()
        }
    }

    override suspend fun getFromLocal(): LocalCompetitions {
        return try {
            val localData = competitionLocalDataSource.CompetitionDao().getAll()
            LocalCompetitions(localData ?: emptyList())
        } catch (e: Exception) {
            Log.e("Repository", "Error reading from local cache", e)
            LocalCompetitions(emptyList())
        }
    }

    private suspend fun getRemoteAndCache(): Resource<DomainCompetitions> {
        return when (val remoteResult = getFromRemote()) {
            is Resource.Success -> {
                remoteResult.data?.competitions?.let { remoteCompetitions ->
                    saveToLocal(remoteCompetitions.toLocalCompetition())
                    Resource.Success(remoteResult.data.toDomainCompetitions())
                } ?: Resource.Error("No data available")
            }
            is Resource.Error -> getLocalData().also {
                if (it is Resource.Error) return Resource.Error("Unknown error")
            }
            else -> Resource.Error("Unknown error")
        }
    }

    private suspend fun getLocalData(): Resource<DomainCompetitions> {
        return try {
            val localData = competitionLocalDataSource.CompetitionDao().getAll()
            if (localData?.isNotEmpty() == true) {
                Resource.Success(LocalCompetitions(localData).toDomainCompetitions())
            } else {
                Resource.Error("No local data")
            }
        } catch (e: Exception) {
            Resource.Error("Local error: ${e.message}")
        }
    }

    override suspend fun getFromRemote(): Resource<RemoteCompetitions> {
        return try {
            competitionRemoteDataSource.getCompetition()
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    override suspend fun saveToLocal(data: List<LocalCompetition>) {
        withContext(Dispatchers.IO) {
            competitionLocalDataSource.CompetitionDao().insert(data)

        }
    }
}
