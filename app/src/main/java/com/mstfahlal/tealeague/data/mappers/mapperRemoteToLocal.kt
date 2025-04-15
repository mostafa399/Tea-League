package com.mstfahlal.tealeague.data.mappers

import com.mstfahlal.tealeague.data.data_sources.local.LocalArea
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetition
import com.mstfahlal.tealeague.data.data_sources.local.LocalCompetitions
import com.mstfahlal.tealeague.data.data_sources.local.LocalCurrentSeason
import com.mstfahlal.tealeague.data.data_sources.local.LocalWinner
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteArea
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCompetition
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCompetitions
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteCurrentSeason
import com.mstfahlal.tealeague.data.data_sources.remote.RemoteWinner

fun RemoteCompetitions.toLocalCompetitions() = LocalCompetitions(
    competitions = competitions?.toLocalCompetition()
)
fun List<RemoteCompetition>.toLocalCompetition() : List<LocalCompetition> {
    return map {
        LocalCompetition(
            area = it.area?.toLocalArea(),
            code = it.code ,
            currentSeason = it.currentSeason?.toLocalCurrentSeason(),
            emblem = it.emblem,
            id = it.id ?: 0,
            lastUpdated = it.lastUpdated,
            name = it.name,
            numberOfAvailableSeasons = it.numberOfAvailableSeasons,
            plan = it.plan,
            type = it.type
        )
    }
}

fun RemoteArea.toLocalArea()= LocalArea(
    code = code, flag = flag, id = id, name = name
)
fun RemoteCurrentSeason.toLocalCurrentSeason() = LocalCurrentSeason(
    currentMatchday =currentMatchday,
    endDate = endDate,
    id = id,
    startDate = startDate,
    winner = winner?.toLocalWinner()
)
fun RemoteWinner.toLocalWinner() = LocalWinner(
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