package com.mstfahlal.tealeague.data.data_sources.remote

import com.mstfahlal.tealeague.utils.Resource

interface ICompetitionRemoteData {
    suspend fun getCompetition(): Resource<RemoteCompetitions>

}