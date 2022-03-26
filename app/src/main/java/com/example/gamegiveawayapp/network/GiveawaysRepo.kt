package com.example.gamegiveawayapp.network

import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.utils.PlatformType
import com.example.gamegiveawayapp.utils.SortType
import retrofit2.Response

interface GiveawaysRepo {
    suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>>
    suspend fun getGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>>
}

class GiveawaysRepoImpl(
    private val giveawaysService: GiveawaysService
) : GiveawaysRepo {

    override suspend fun getAllGiveaways(sortedBy: SortType): Response<List<Giveaways>> =
        giveawaysService.getAllGiveaways(sortedBy.realValue)

    override suspend fun getGiveawaysByPlatform(platform: PlatformType): Response<List<Giveaways>> =
        giveawaysService.getGiveawaysByPlatform(platform.realValue)

}