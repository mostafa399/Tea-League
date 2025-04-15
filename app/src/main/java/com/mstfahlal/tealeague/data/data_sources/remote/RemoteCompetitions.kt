package com.mstfahlal.tealeague.data.data_sources.remote

data class RemoteCompetitions(
    val competitions: List<RemoteCompetition>?,
    val count: Int?,
    val filters: Filters?
)
data class RemoteCompetition(
    val area: RemoteArea?,
    val code: String?,
    val currentSeason: RemoteCurrentSeason?,
    val emblem: String?,
    val id: Int?,
    val lastUpdated: String?,
    val name: String?,
    val numberOfAvailableSeasons: Int?,
    val plan: String?,
    val type: String?
)
data class RemoteArea(
    val code: String?,
    val flag: String?,
    val id: Int?,
    val name: String?
)
data class RemoteCurrentSeason(
    val currentMatchday: Int?,
    val endDate: String?,
    val id: Int?,
    val startDate: String?,
    val winner: RemoteWinner?
)
data class RemoteWinner(
    val address: String?,
    val clubColors: String?,
    val crest: String?,
    val founded: Int?,
    val id: Int?,
    val lastUpdated: String?,
    val name: String?,
    val shortName: String?,
    val tla: String?,
    val venue: String?,
    val website: String?
)
data class Filters(
    val client: String?
)