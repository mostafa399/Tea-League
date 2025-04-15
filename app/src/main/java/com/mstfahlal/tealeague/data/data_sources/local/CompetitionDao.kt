package com.mstfahlal.tealeague.data.data_sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CompetitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( listOfCompetition : List<LocalCompetition>)

    @Query("SELECT * FROM CompetitionsTable")
    fun getAll(): List<LocalCompetition>?
}