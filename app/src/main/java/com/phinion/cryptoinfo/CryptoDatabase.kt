package com.phinion.cryptoinfo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency

@Database(entities = [CryptoCurrency::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}