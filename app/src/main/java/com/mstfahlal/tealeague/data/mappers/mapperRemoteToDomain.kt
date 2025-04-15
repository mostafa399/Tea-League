package com.mstfahlal.tealeague.data.mappers

import com.mstfahlal.tealeague.data.data_sources.remote.RemoteArea
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCompetition
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCompetitions
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCurrentSeason
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteWinner
import com.mstfahlal.tealeague.domain.model.DomainArea
import com.mstfahlal.tealeague.domain.model.DomainCompetition
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.domain.model.DomainCurrentSeason
import com.mstfahlal.tealeague.domain.model.DomainWinner

fun RemoteCompetitions.toDomainCompetitions() = DomainCompetitions(
    competitions = competitions?.toDomainCompetition(),
    count = count,
)
fun List<RemoteCompetition>.toDomainCompetition() : List<DomainCompetition>{
    return map {
        DomainCompetition(
            area = it.area?.toDomainArea(),
            code = it.code ,
            currentSeason = it.currentSeason?.toDomainCurrentSeason(),
            emblem = it.emblem,
            id = it.id,
            lastUpdated = it.lastUpdated,
            name = it.name,
            numberOfAvailableSeasons = it.numberOfAvailableSeasons,
            plan = it.plan,
            type = it.type
        )
    }
}

fun RemoteArea.toDomainArea()= DomainArea(
    code = code, flag = flag, id = id, name = name
)
fun RemoteCurrentSeason.toDomainCurrentSeason() = DomainCurrentSeason(
    currentMatchday = currentMatchday,
    endDate = endDate,
    id = id,
    startDate = startDate,
    winner  = winner?.toDomainWinner()
)
fun RemoteWinner.toDomainWinner() = DomainWinner(
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