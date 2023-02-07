package com.carteagal.baz_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carteagal.baz_android.data.local.entities.OrderBookEntity

@Dao
interface OrderBookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetOrderBook(orderBook: OrderBookEntity)

    @Query("SELECT * FROM order_book_table WHERE book_name = :bookName")
    suspend fun getBook(bookName: String): OrderBookEntity

    @Query("DELETE FROM order_book_table WHERE book_name = :bookName")
    suspend fun deleteBook(bookName: String)
}