package com.mstfahlal.tealeague.data.mappers

import com.mstfahlal.tealeague.data.data_sources.local.LocalArea
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetition
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetitions
import com.mstfahlal.tealeague.data.data_sources.local.LocalCurrentSeason
import com.mstfahlal.tealeague.data.data_sources.local.LocalWinner
import com.mstfahlal.tealeague.domain.model.DomainArea
import com.mstfahlal.tealeague.domain.model.DomainCompetition
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.domain.model.DomainCurrentSeason
import com.mstfahlal.tealeague.domain.model.DomainWinner

fun LocalCompetitions.toDomainCompetitions() : DomainCompetitions {
    return DomainCompetitions(
        competitions = this.competitions?.toDomainCompetition(),
        count = this.competitions?.size
    )
}
fun List<LocalCompetition>.toDomainCompetition() : List<DomainCompetition> {
    return map {
        DomainCompetition(
            area=it.area?.toDomainArea(),
            code=it.code,
            currentSeason =  it.currentSeason?.toDomainCurrentSeason(),
            emblem = it.emblem,
            id = it.id,
            lastUpdated = it.lastUpdated,
            name =  it.name,
            numberOfAvailableSeasons = it.numberOfAvailableSeasons,
            plan = it.plan,
            type = it.type

        )
    }
}

fun LocalArea.toDomainArea()= DomainArea(
    code = code, flag = flag, id = id, name = name
)
fun LocalCurrentSeason.toDomainCurrentSeason() = DomainCurrentSeason(
    currentMatchday =currentMatchday,
    endDate = endDate,
    id = id,
    startDate = startDate,
    winner = winner?.toDomainWinner()
)
fun LocalWinner.toDomainWinner() = DomainWinner(
    address = address,
    clubColors=clubColors,
    crest=crest,
    founded=founded,
    id=id,
    lastUpdated=lastUpdated,
    name=name,
    shortName=shortName,
    tla=tla,
    venue=venue,
    website = website
)