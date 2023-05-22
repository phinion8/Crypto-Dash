package com.phinion.cryptoinfo.di

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.phinion.cryptoinfo.Converter
import com.phinion.cryptoinfo.CryptoDatabase
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CryptoDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCryptoDao(cryptoDatabase: CryptoDatabase) = cryptoDatabase.cryptoDao()
}