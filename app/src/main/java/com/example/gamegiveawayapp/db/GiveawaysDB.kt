package com.example.gamegiveawayapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.gamegiveawayapp.model.Giveaways

@Database(
    entities = [Giveaways::class],
    version = 1
)
abstract class GiveawaysDB : RoomDatabase() {

    abstract fun getGiveawaysDAO(): GiveawaysDAO
}

@Dao
interface GiveawaysDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)

    @Query("SELECT * FROM Giveaways")
    suspend fun getGiveaways(): List<Giveaways>

    @Query("SELECT * FROM Giveaways WHERE id = :searchId")
    suspend fun getGiveawaysById(searchId: Int): Giveaways

    @Query("SELECT * FROM Giveaways WHERE platforms = :platform")
    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>

    @Delete
    suspend fun deleteGiveaways(giveaways: List<Giveaways>)
}