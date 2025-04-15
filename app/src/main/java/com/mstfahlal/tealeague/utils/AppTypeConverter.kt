package com.mstfahlal.tealeague.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mstfahlal.tealeague.data.data_sources.local.LocalArea
import com.mstfahlal.tealeague.data.data_sources.local.LocalCurrentSeason
import com.mstfahlal.tealeague.data.data_sources.local.LocalWinner

class AppTypeConverter {
    @TypeConverter
    fun fromArea(area: LocalArea): String {
        return Gson().toJson(area)
    }
    @TypeConverter
    fun toArea(json: String): LocalArea {
        return Gson().fromJson(json, LocalArea::class.java)
    }

    @TypeConverter
    fun fromCurrentSeason(local: LocalCurrentSeason?): String {
        return Gson().toJson(local)
    }

    @TypeConverter
    fun toCurrentSeason(json: String?): LocalCurrentSeason? {
        return Gson().fromJson(json, LocalCurrentSeason::class.java)
    }

    @TypeConverter
    fun fromLocalWinner(winner: LocalWinner): String {
        return Gson().toJson(winner)
    }

    @TypeConverter
    fun toLocalWinner(json: String): LocalWinner {
        return Gson().fromJson(json, LocalWinner::class.java)
    }

}