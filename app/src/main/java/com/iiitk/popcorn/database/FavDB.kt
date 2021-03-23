package com.iiitk.popcorn.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =[ FavEntity::class] ,version = 2)
abstract class FavDB : RoomDatabase() {
    abstract fun favDao():FavDao
}