package com.airnow.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.airnow.data.converter.WeatherConverter
import com.airnow.data.db.dao.LocationDao
import com.airnow.data.db.dao.PostDao
import com.airnow.data.db.dao.SourceDao
import com.airnow.data.db.dao.WeatherDao
import com.airnow.data.model.Location
import com.airnow.data.model.Post
import com.airnow.data.model.Source
import com.airnow.data.model.Weather

@Database(entities = [Source::class, Post::class, Location::class, Weather::class],
        version = 4)
@TypeConverters(WeatherConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val POST_TABLE = "rss"
        const val SOURCE_TABLE = "source"
        const val WEATHER_TABLE = "weather"
        const val LOCATION_TABLE = "location"
        const val APP_DATABASE_NAME = "airnow.db"
    }

    abstract fun postDao(): PostDao

    abstract fun sourceDao(): SourceDao

    abstract fun weatherDao(): WeatherDao

    abstract fun locationDao(): LocationDao

}