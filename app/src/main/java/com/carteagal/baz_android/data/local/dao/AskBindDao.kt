package com.carteagal.baz_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.carteagal.baz_android.data.local.entities.AskBindEntity

@Dao
interface AskBindDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAskBindsList(asks: List<AskBindEntity>)

    @Query("SELECT * FROM ask_bind_table WHERE book = :book")
    suspend fun getAllAsksBindsList(book: String): List<AskBindEntity>

    @Query("DELETE FROM ask_bind_table WHERE book = :book")
    suspend fun deleteAsksBindsList(book: String)
}