package com.mstfahlal.tealeague.domain.usecase

import android.content.Context
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.domain.repository.ICompetitionRepository
import com.mstfahlal.tealeague.utils.Resource
import javax.inject.Inject

class GetAllCompetition @Inject constructor(
    private val CompetitionsRepo : ICompetitionRepository,
):IGetAllCompetition {
    override suspend fun getAllCompetitions(context: Context): Resource<DomainCompetitions> {
        return CompetitionsRepo.getCompetition(context)
    }

}