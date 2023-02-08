package com.carteagal.baz_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.carteagal.baz_android.data.local.entities.TickerEntity

@Dao
interface TickerDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertTicker(tickerDao: TickerEntity)

    @Query("SELECT * FROM ticker_table WHERE book_name = :bookName")
    suspend fun getTicker(bookName: String): TickerEntity

    @Query("DELETE FROM ticker_table WHERE book_name = :bookName")
    suspend fun deleteTicker(bookName: String)
}