package com.mstfahlal.tealeague.data.data_sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CompetitionsTable")
data class LocalCompetition(
    val area: LocalArea?= LocalArea(null,null,null,null),
    val code: String?="",
    val currentSeason: LocalCurrentSeason?= LocalCurrentSeason(null,null,null,null,null),
    val emblem: String?,
    @PrimaryKey
    val id: Int,
    val lastUpdated: String?,
    val name: String?,
    val numberOfAvailableSeasons: Int?,
    val plan: String?,
    val type: String?
)
data class LocalArea(
    val code: String?,
    val flag: String?,
    val id: Int?,
    val name: String?
)
data class LocalCurrentSeason(
    val currentMatchday: Int?,
    val endDate: String?,
    val id: Int?,
    val startDate: String?,
    val winner: LocalWinner?
)
data class LocalWinner(
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
