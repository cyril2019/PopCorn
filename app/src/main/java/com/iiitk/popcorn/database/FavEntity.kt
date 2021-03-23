package com.iiitk.popcorn.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourites")
data class FavEntity(
    @PrimaryKey
    val favId:Int,
    val name:String,

)