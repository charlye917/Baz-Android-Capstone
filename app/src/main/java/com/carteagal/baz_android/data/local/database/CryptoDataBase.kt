package com.carteagal.baz_android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carteagal.baz_android.data.local.dao.AvailableBookDao
import com.carteagal.baz_android.data.local.dao.AskBindDao
import com.carteagal.baz_android.data.local.dao.TickerDao
import com.carteagal.baz_android.data.local.dao.TickerRxDao
import com.carteagal.baz_android.data.local.entities.AskBindEntity
import com.carteagal.baz_android.data.local.entities.AvailableBookEntity
import com.carteagal.baz_android.data.local.entities.TickerEntity

@Database(
    entities = [AvailableBookEntity::class, AskBindEntity::class, TickerEntity::class],
    version = 1
)
abstract class CryptoDataBase : RoomDatabase(){
    abstract fun getAvailableBooksDao(): AvailableBookDao
    abstract fun getAskBindDao(): AskBindDao
    abstract fun getTickerDao(): TickerDao
    abstract fun getTickerRxDao(): TickerRxDao
}