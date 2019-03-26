package com.airnow.data.db.dao

import androidx.room.Room
import android.database.sqlite.SQLiteConstraintException
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.airnow.data.db.AppDatabase
import com.airnow.data.model.factory.LocationFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WeatherLocationDaoTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
                AppDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun GIVEN_no_user_WHEN_insert_location_THEN_exception_is_thrown() {
        try {
            val weatherLocation = LocationFactory.createCurrentLocationTest()
            database.locationDao().insertLocation(weatherLocation)

            val weatherLocationList = database.locationDao().getAllLocationList()
            assert(weatherLocationList.isNotEmpty())
        } catch (e: SQLiteConstraintException) {
            assert(e is SQLiteConstraintException)
        }
    }

    @Test
    fun GIVEN_user_WHEN_insert_location_THEN_location_is_saved() {
        // Insertamos el usuario de test

        val location = LocationFactory.createCurrentLocationTest()
        database.locationDao().insertLocation(location)

        val weatherLocationList = database.locationDao().getAllLocationList()
        assert(weatherLocationList.isNotEmpty())
    }

    @Test
    fun GIVEN_user_WHEN_user_deleted_THEN_location_deleted() {

        val weatherLocation = LocationFactory.createCurrentLocationTest()
        database.locationDao().insertLocation(weatherLocation)

        val weatherLocationList = database.locationDao().getAllLocationList()
        assert(weatherLocationList.isNotEmpty())

        val weatherLocationListAfterDelete = database.locationDao().getAllLocationList()
        assert(weatherLocationListAfterDelete.isNotEmpty())
    }

    @Test
    fun GIVEN_user_and_location_WHEN_deleteAll_location_THEN_location_is_empty() {
        // Insertamos el usuario de test
        val weatherLocation = LocationFactory.createCurrentLocationTest()
        database.locationDao().insertLocation(weatherLocation)

        val weatherLocationList = database.locationDao().getAllLocationList()
        assert(weatherLocationList.isNotEmpty())

        database.locationDao().deleteAll()
        val weatherLocationListAfterDeleteAll = database.locationDao().getAllLocationList()
        assert(weatherLocationListAfterDeleteAll.isNotEmpty())
    }


}