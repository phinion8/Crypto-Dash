package com.phinion.cryptoinfo.di

import com.phinion.cryptoinfo.apis.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApiService(): ApiInterface =
        Retrofit.Builder()
            .run {
                baseUrl("https://api.coinmarketcap.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }.create(ApiInterface::class.java)

}