package com.example.gamegiveawayapp.network

import com.example.gamegiveawayapp.model.Giveaways
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiveawaysService {

    @GET(GIVEAWAYS_PATH)
    suspend fun getAllGiveaways(
        @Query("sort-by") orderBy: String
    ): Response<List<Giveaways>>

    @GET(GIVEAWAYS_PATH)
    suspend fun getGiveawaysByPlatform(
        @Query("platform") platform: String
    ): Response<List<Giveaways>>

    companion object {
        const val BASE_URL = "https://www.gamerpower.com/api/"
        private const val GIVEAWAYS_PATH = "giveaways"
    }
}