package com.carteagal.baz_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carteagal.baz_android.data.local.entities.TickerEntity

@Dao
interface TickerRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTickerRx(tickerDao: TickerEntity)

    @Query("SELECT * FROM ticker_table WHERE full_name = :bookName")
    fun getTickerRx(bookName: String): TickerEntity?

    @Query("DELETE FROM ticker_table WHERE full_name = :bookName")
    fun deleteTickerRx(bookName: String)
}