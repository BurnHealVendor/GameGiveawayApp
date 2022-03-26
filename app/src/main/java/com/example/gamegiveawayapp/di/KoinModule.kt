package com.example.gamegiveawayapp.di

import android.content.Context
import androidx.room.Room
import com.example.gamegiveawayapp.db.DBRepo
import com.example.gamegiveawayapp.db.DBRepoImpl
import com.example.gamegiveawayapp.db.GiveawaysDAO
import com.example.gamegiveawayapp.db.GiveawaysDB
import com.example.gamegiveawayapp.network.GiveawaysRepo
import com.example.gamegiveawayapp.network.GiveawaysRepoImpl
import com.example.gamegiveawayapp.network.GiveawaysService
import com.example.gamegiveawayapp.viewmodel.GiveawaysViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    fun providesNetworkService(okHttpClient: OkHttpClient): GiveawaysService =
        Retrofit.Builder()
            .baseUrl(GiveawaysService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GiveawaysService::class.java)

    fun provideGiveawaysRepo(networkService: GiveawaysService): GiveawaysRepo =
        GiveawaysRepoImpl(networkService)

    single { providesLoggingInterceptor() }
    single { providesNetworkService(get()) }
    single { providesOkHttpClient(get()) }
    single { provideGiveawaysRepo(get()) }
}

val applicationModule = module {

    fun providesGiveawaysDB(context: Context): GiveawaysDB =
        Room.databaseBuilder(
            context,
            GiveawaysDB::class.java,
            "giveaways-db"
        ).build()

    fun providesGiveawaysDAO(giveawaysDB: GiveawaysDB): GiveawaysDAO =
        giveawaysDB.getGiveawaysDAO()

    fun providesDBRepo(databaseDAO: GiveawaysDAO): DBRepo =
        DBRepoImpl(databaseDAO)

    single { providesGiveawaysDB(androidContext()) }
    single { providesGiveawaysDAO(get()) }
    single { providesDBRepo(get()) }
}

val viewModelModule = module {

    viewModel { GiveawaysViewModel(get(), get()) }
}