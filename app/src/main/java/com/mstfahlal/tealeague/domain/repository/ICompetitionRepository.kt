package com.mstfahlal.tealeague.domain.repository

import android.content.Context
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetition
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetitions
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCompetitions
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.utils.Resource

interface ICompetitionRepository {
    suspend fun getCompetition(context: Context): Resource<DomainCompetitions>
    suspend fun getFromLocal(): LocalCompetitions
    suspend fun getFromRemote(): Resource<RemoteCompetitions>
    suspend fun saveToLocal(data :List<LocalCompetition>)
}