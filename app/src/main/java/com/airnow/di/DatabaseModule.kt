package com.airnow.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.airnow.App
import com.airnow.data.api.INetworkDataSource
import com.airnow.data.api.NetworkDataSource
import com.airnow.data.api.NetworkSystem
import com.airnow.data.api.NetworkSystemAbstract
import com.airnow.data.db.AppDatabase
import com.airnow.data.db.MIGRATION_1_2
import com.airnow.data.db.MIGRATION_2_3
import com.airnow.data.db.MIGRATION_3_4
import com.airnow.data.model.Source
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(app: App): AppDatabase {
        var appDatabase: AppDatabase? = null
        appDatabase = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.APP_DATABASE_NAME
        ).addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                insertSources(appDatabase)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                insertSources(appDatabase)
            }
        }).build()

        return appDatabase
    }

    private fun insertSources(appDatabase: AppDatabase?) {
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase?.sourceDao()?.insertSources(
                listOf(
                    Source(
                        1,
                        "South Coast AQMD",
                        "https://twitrss.me/twitter_user_to_rss/?user=SouthCoastAQMD"
                    ),
                    Source(
                        2,
                        "Bay Area Air Quality",
                        "https://twitrss.me/twitter_user_to_rss/?user=AirDistrict"
                    ),
                    Source(
                        3,
                        "Ventura Co. APCD",
                        "https://twitrss.me/twitter_user_to_rss/?user=VCAPCD"
                    ),
                    Source(
                        4,
                        "CDC Asthma Program",
                        "https://twitrss.me/twitter_user_to_rss/?user=CDCasthma"
                    ),
                    Source(
                        5,
                        "Environmental Agency",
                        "https://twitrss.me/twitter_user_to_rss/?user=EPAair"
                    ),
                    Source(
                        6,
                        "EPA Government Airnow",
                        "https://twitrss.me/twitter_user_to_rss/?user=airnow"
                    ),
                    Source(
                        7,
                        "MARC AirQ Program",
                        "https://twitrss.me/twitter_user_to_rss/?user=airqkc"
                    ),
                    Source(
                            8,
                            "BBC Earth Channel",
                            "https://www.youtube.com/feeds/videos.xml?channel_id=UCwmZiChSryoWQCZMIQezgTg"
                    ),
                    Source(
                            9,
                            "The Fourth Wave",
                            "https://medium.com/feed/the-fourth-wave"
                    )
                )
            )
        }
    }


    @Provides
    @Singleton
    fun providesRssDao(database: AppDatabase) = database.postDao()

    @Provides
    @Singleton
    fun providesSourceDao(database: AppDatabase) = database.sourceDao()

    @Provides
    @Singleton
    fun providesWeatherDao(database: AppDatabase) = database.weatherDao()

    @Provides
    @Singleton
    fun providesLocationDao(database: AppDatabase) = database.locationDao()

    @Provides
    @Singleton
    fun provideNetworkSystem(app: App) =
            NetworkSystem(app) as NetworkSystemAbstract

    @Provides
    @Singleton
    fun provideNetworkDataSource(networkSystemBase: NetworkSystemAbstract) =
            NetworkDataSource(networkSystemBase) as INetworkDataSource

}