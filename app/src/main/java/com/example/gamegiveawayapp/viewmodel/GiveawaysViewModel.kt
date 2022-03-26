package com.example.gamegiveawayapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamegiveawayapp.db.DBRepo
import com.example.gamegiveawayapp.model.Giveaways
import com.example.gamegiveawayapp.network.GiveawaysRepo
import com.example.gamegiveawayapp.utils.GiveawaysState
import com.example.gamegiveawayapp.utils.PlatformType
import com.example.gamegiveawayapp.utils.SortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GiveawaysViewModel(
    private val networkRepo: GiveawaysRepo,
    private val dbRepo: DBRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    // private val coroutineScope: CoroutineScope = CoroutineScope(ioDispatcher)
) : ViewModel() {

    init {
        Log.d("GiveawaysViewModel", "VIEWMODEL created")
    }

    var platform: PlatformType = PlatformType.ANDROID

    private val _sortedGiveaways: MutableLiveData<GiveawaysState> = MutableLiveData(GiveawaysState.LOADING)

    val giveaways: LiveData<GiveawaysState> get() = _sortedGiveaways

    fun getSortedGiveaways(sortBy: SortType = SortType.DATE) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getAllGiveaways(sortBy)
                if (response.isSuccessful) {
                    response.body()?.let {
                        dbRepo.insertGiveaways(it)
                        val localData = dbRepo.getGiveaways()
                        _sortedGiveaways.postValue(GiveawaysState.SUCCESS(localData))
                    } ?: throw Exception("Response is null")
                }
                else {
                    throw Exception("No successful response")
                }
            }
            catch (e: Exception) {
                _sortedGiveaways.postValue(GiveawaysState.ERROR(e))
                loadFromDB()
            }
        }
    }

    private suspend fun loadFromDB() {
        try {
            val localData = dbRepo.getGiveaways()
            _sortedGiveaways.postValue(GiveawaysState.SUCCESS(localData, isLocalData = true))
        }
        catch (e: Exception) {
            _sortedGiveaways.postValue(GiveawaysState.ERROR(e))
        }
    }

    fun getGiveawaysByPlatform() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = networkRepo.getGiveawaysByPlatform(platform)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _sortedGiveaways.postValue(GiveawaysState.SUCCESS(it))
                    } ?: throw Exception("Response is null")
                }
                else {
                    throw Exception("No successful response")
                }
            }
            catch (e: Exception) {
                _sortedGiveaways.postValue(GiveawaysState.ERROR(e))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GiveawaysViewModel", "VIEWMODEL destroyed")
    }
}