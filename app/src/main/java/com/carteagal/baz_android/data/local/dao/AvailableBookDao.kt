package com.carteagal.baz_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.carteagal.baz_android.data.model.AvailableBookResponse
import com.carteagal.baz_android.data.local.entities.AvailableBookEntity

@Dao
interface AvailableBookDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAllBooks(books: List<AvailableBookEntity>)

    @Query("SELECT * FROM available_book_table")
    suspend fun getAllBooks(): List<AvailableBookEntity>

    @Query("DELETE FROM available_book_table")
    suspend fun deleteAllBooks()
}