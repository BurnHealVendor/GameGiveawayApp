package com.example.gamegiveawayapp.utils

import com.example.gamegiveawayapp.model.Giveaways

sealed class GiveawaysState {
    object  LOADING : GiveawaysState()
    class  SUCCESS<T>(val giveaways: T, isLocalData: Boolean = false) : GiveawaysState()
    class  ERROR(val error: Throwable) : GiveawaysState()
}
