package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carteagal.baz_android.data.model.TickerResponse

@Entity(tableName = "ticker_table")
data class TickerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_name") val bookName: String? = null,
    @ColumnInfo(name = "volume") val volume: String? = null,
    @ColumnInfo(name = "high") val high: String? = null,
    @ColumnInfo(name = "last") val last: String? = null,
    @ColumnInfo(name = "low") val low: String? = null,
    @ColumnInfo(name = "vwap") val vwap: String? = null,
    @ColumnInfo(name = "ask") val ask: String? = null,
    @ColumnInfo(name = "bid") val bid: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: String? = null
)

fun TickerResponse.tickerResponseToEntity(): TickerEntity =
    TickerEntity(
        bookName = book,
        volume = volume,
        high = high,
        last = last,
        low = low,
        vwap = vwap,
        ask = ask,
        bid = bid,
        createdAt = createdAt
    )

fun TickerEntity.tickerEntityToResponse(): TickerResponse =
    TickerResponse(
        book = bookName!!,
        volume = volume!!,
        high = high!!,
        last = last!!,
        low = low!!,
        vwap = vwap!!,
        ask = ask!!,
        bid = bid!!,
        createdAt = createdAt!!
    )