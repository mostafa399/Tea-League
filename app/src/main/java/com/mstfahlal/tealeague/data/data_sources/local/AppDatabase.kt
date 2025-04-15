package com.mstfahlal.tealeague.data.data_sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mstfahlal.tealeague.utils.AppTypeConverter

@TypeConverters(value = [AppTypeConverter::class])
@Database(entities = [LocalCompetition::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun CompetitionDao() : CompetitionDao
}