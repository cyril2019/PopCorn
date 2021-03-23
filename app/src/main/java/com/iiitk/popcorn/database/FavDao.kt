package com.iiitk.popcorn.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import retrofit2.http.DELETE

@Dao
interface FavDao {
    @Insert
    fun addFav(fav:FavEntity)

    @Delete
    fun remFav(fav:FavEntity)

    @Query("SELECT * FROM favourites WHERE favId=:id")
    fun getMovie(id: Int):FavEntity
}