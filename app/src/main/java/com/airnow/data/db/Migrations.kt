package com.airnow.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.airnow.data.db.AppDatabase.Companion.POST_TABLE
import com.airnow.data.db.AppDatabase.Companion.SOURCE_TABLE

/**
 * Adds sources table to the db.
 */
val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `$SOURCE_TABLE` (`url` TEXT NOT NULL, `name` TEXT NOT NULL, `is_active` INTEGER NOT NULL, PRIMARY KEY(`url`))")
    }
}

/**
 * Adds id field to the `source` table and adds `source_id` as foreign key to posts table.
 */
val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // add `id` to source table and make it primary key
        database.execSQL("CREATE TABLE IF NOT EXISTS `source_temp` (`url` TEXT NOT NULL, `name` TEXT NOT NULL, `is_active` INTEGER NOT NULL, `id` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        database.execSQL("INSERT INTO source_temp (url, name, is_active) SELECT url, name, is_active FROM `$SOURCE_TABLE`;")
        database.execSQL("DROP TABLE IF EXISTS `$SOURCE_TABLE`;")
        database.execSQL("ALTER TABLE source_temp RENAME TO `$SOURCE_TABLE`;")

        // add source_id
        database.execSQL("CREATE TABLE IF NOT EXISTS `post_temp` (`source_id` INTEGER DEFAULT 0,`bookmarked` INTEGER NOT NULL, `link` TEXT NOT NULL, `pub_date` TEXT NOT NULL, `pub_date_timestamp` INTEGER NOT NULL, `title` TEXT NOT NULL, `author` TEXT NOT NULL, `content_raw` TEXT NOT NULL, `channel_title` TEXT NOT NULL, `channel_image_url` TEXT NOT NULL, `channel_link` TEXT NOT NULL, `content_image` TEXT NOT NULL, `content` TEXT NOT NULL, PRIMARY KEY(`link`), FOREIGN KEY (source_id) REFERENCES `$SOURCE_TABLE`(id) ON DELETE CASCADE);")
        database.execSQL("INSERT INTO post_temp (bookmarked, link, pub_date, pub_date_timestamp, title, author, content_raw, channel_title, channel_image_url, channel_link, content_image, content) SELECT bookmarked, link, pub_date, pub_date_timestamp ,title, author, content_raw, channel_title, channel_image_url, channel_link, content_image, content FROM ${AppDatabase.POST_TABLE};")
        database.execSQL("DROP TABLE IF EXISTS `$POST_TABLE`;")
        database.execSQL("ALTER TABLE post_temp RENAME TO `$POST_TABLE`;")

        // bind posts to sources
        database.execSQL("UPDATE $POST_TABLE SET source_id = (SELECT id FROM $SOURCE_TABLE WHERE url = $POST_TABLE.channel_link)")
    }
}


val MIGRATION_3_4: Migration = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE  INDEX `index_rss_source_id` ON `$POST_TABLE` (`source_id`)")
    }
}


val MIGRATION_1_4: Migration = object : Migration(1, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {

        // add `id` to source table and make it primary key
        database.execSQL("CREATE TABLE IF NOT EXISTS `source_temp` (`url` TEXT NOT NULL, `name` TEXT NOT NULL, `is_active` INTEGER NOT NULL, `id` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        database.execSQL("INSERT INTO source_temp (url, name, is_active) SELECT url, name, is_active FROM `$SOURCE_TABLE`;")
        database.execSQL("DROP TABLE IF EXISTS `$SOURCE_TABLE`;")
        database.execSQL("ALTER TABLE source_temp RENAME TO `$SOURCE_TABLE`;")

        // add source_id
        database.execSQL("CREATE TABLE IF NOT EXISTS `post_temp` (`source_id` INTEGER DEFAULT 0,`bookmarked` INTEGER NOT NULL, `link` TEXT NOT NULL, `pub_date` TEXT NOT NULL, `pub_date_timestamp` INTEGER NOT NULL, `title` TEXT NOT NULL, `author` TEXT NOT NULL, `content_raw` TEXT NOT NULL, `channel_title` TEXT NOT NULL, `channel_image_url` TEXT NOT NULL, `channel_link` TEXT NOT NULL, `content_image` TEXT NOT NULL, `content` TEXT NOT NULL, PRIMARY KEY(`link`), FOREIGN KEY (source_id) REFERENCES `$SOURCE_TABLE`(id) ON DELETE CASCADE);")
        database.execSQL("INSERT INTO post_temp (bookmarked, link, pub_date, pub_date_timestamp, title, author, content_raw, channel_title, channel_image_url, channel_link, content_image, content) SELECT bookmarked, link, pub_date, pub_date_timestamp ,title, author, content_raw, channel_title, channel_image_url, channel_link, content_image, content FROM ${AppDatabase.POST_TABLE};")
        database.execSQL("DROP TABLE IF EXISTS `$POST_TABLE`;")
        database.execSQL("ALTER TABLE post_temp RENAME TO `$POST_TABLE`;")

        // bind posts to sources
        database.execSQL("UPDATE $POST_TABLE SET source_id = (SELECT id FROM $SOURCE_TABLE WHERE url = $POST_TABLE.channel_link)")
        database.execSQL("CREATE  INDEX `index_rss_source_id` ON `$POST_TABLE` (`source_id`)")
    }
}