package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_book_table")
data class OrderBookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_name") val bookName: String?,
    @ColumnInfo(name = "update_at") val updateAt: String?,
    @ColumnInfo(name = "sequence") val sequence: String?,
)