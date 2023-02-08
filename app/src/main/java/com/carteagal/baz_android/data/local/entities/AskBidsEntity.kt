package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.carteagal.baz_android.data.model.AskBindsResponse
import retrofit2.Converter

@Entity(tableName = "ask_bids_table")
data class AskBidsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book") val book: String? = null,
    @ColumnInfo(name = "price") val price: String? = null,
    @ColumnInfo(name = "amount") val amount: String? = null
)

@TypeConverter
fun List<AskBindsResponse>.askBindsResponseToEntity(): List<AskBidsEntity> =
    map {
        AskBidsEntity(
            book = it.book,
            price = it.price,
            amount = it.amount
        )
    }

@TypeConverter
fun List<AskBidsEntity>.askBindsEntityToResponse(): List<AskBindsResponse> =
    map {
        AskBindsResponse(
            book = it.book,
            price = it.price,
            amount = it.amount,

        )
    }