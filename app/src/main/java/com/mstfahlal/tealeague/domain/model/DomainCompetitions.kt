package com.mstfahlal.tealeague.domain.model

data class DomainCompetitions(
    val competitions: List<DomainCompetition>?,
    val count: Int?,
)
data class DomainCompetition(
    val area: DomainArea?,
    val code: String?,
    val currentSeason: DomainCurrentSeason?,
    val emblem: String?,
    val id: Int?,
    val lastUpdated: String?,
    val name: String?,
    val numberOfAvailableSeasons: Int?,
    val plan: String?,
    val type: String?
)
data class DomainArea(
    val code: String?,
    val flag: String?,
    val id: Int?,
    val name: String?
)
data class DomainCurrentSeason(
    val currentMatchday: Int?,
    val endDate: String?,
    val id: Int?,
    val startDate: String?,
    val winner: DomainWinner?
)
data class DomainWinner(
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
