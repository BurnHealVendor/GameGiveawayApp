package com.example.gamegiveawayapp.db

import androidx.room.*
import com.example.gamegiveawayapp.model.Giveaways

interface DBRepo {
    suspend fun insertGiveaways(newGiveaways: List<Giveaways>)

    suspend fun getGiveaways(): List<Giveaways>

    suspend fun getGiveawaysById(searchId: Int): Giveaways

    suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways>

    suspend fun deleteGiveaways(giveaways: List<Giveaways>)
}

class DBRepoImpl(
    private val giveawaysDatabase: GiveawaysDAO
) : DBRepo {
    override suspend fun insertGiveaways(newGiveaways: List<Giveaways>) {
        giveawaysDatabase.insertGiveaways(newGiveaways)
    }

    override suspend fun getGiveaways(): List<Giveaways> {
        return giveawaysDatabase.getGiveaways()
    }

    override suspend fun getGiveawaysById(searchId: Int): Giveaways {
        return giveawaysDatabase.getGiveawaysById(searchId)
    }

    override suspend fun getGiveawaysByPlatform(platform: String): List<Giveaways> {
        return giveawaysDatabase.getGiveawaysByPlatform(platform)
    }

    override suspend fun deleteGiveaways(giveaways: List<Giveaways>) {
        return giveawaysDatabase.deleteGiveaways(giveaways)
    }

}