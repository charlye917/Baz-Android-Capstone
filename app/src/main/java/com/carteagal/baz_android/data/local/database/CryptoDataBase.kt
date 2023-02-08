package com.carteagal.baz_android.data.local.database

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.carteagal.baz_android.data.local.dao.AvailableBookDao
import com.carteagal.baz_android.data.local.dao.OrderBookDao
import com.carteagal.baz_android.data.local.dao.TickerDao
import com.carteagal.baz_android.data.local.entities.AvailableBookEntity
import com.carteagal.baz_android.data.local.entities.OrderBookEntity
import com.carteagal.baz_android.data.local.entities.TickerEntity

@Database(
    entities = [AvailableBookEntity::class, OrderBookEntity::class, TickerEntity::class],
    version = 1
)
abstract class CryptoDataBase : RoomDatabase(){
    abstract fun getAvailableBooksDao(): AvailableBookDao
    abstract fun getOrderBooks(): OrderBookDao
    abstract fun getTickerDao(): TickerDao
}