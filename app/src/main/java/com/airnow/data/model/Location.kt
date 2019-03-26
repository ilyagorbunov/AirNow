package com.airnow.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.airnow.data.db.AppDatabase
import com.airnow.ui.adapter.diff.Diffable

@Entity(tableName = AppDatabase.LOCATION_TABLE)
class Location(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var cityName: String = ""
) : Diffable {

    override fun isSame(item: Any) = equals(item)

    override fun hasSameContentWith(item: Any) = equals(item)

}
