package com.airnow.data.db.dao

import androidx.room.*
import com.airnow.data.db.AppDatabase.Companion.LOCATION_TABLE
import com.airnow.data.model.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: Location)

    @Delete
    fun deleteLocation(location: Location)

    @Query("DELETE FROM $LOCATION_TABLE")
    fun deleteAll()

    @Query("SELECT * FROM $LOCATION_TABLE")
    fun getAllLocationList():List<Location>
}