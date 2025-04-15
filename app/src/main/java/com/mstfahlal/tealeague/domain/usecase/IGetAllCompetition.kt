package com.mstfahlal.tealeague.domain.usecase

import android.content.Context
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.utils.Resource

interface IGetAllCompetition {
    suspend fun getAllCompetitions(): Resource<DomainCompetitions>

}