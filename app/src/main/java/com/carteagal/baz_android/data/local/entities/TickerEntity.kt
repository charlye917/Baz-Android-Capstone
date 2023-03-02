package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carteagal.baz_android.domain.model.TickerUI

@Entity(tableName = "ticker_table")
data class TickerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "full_name") val fullName: String? = null,
    @ColumnInfo(name = "book_name") val bookName: String? = null,
    @ColumnInfo(name = "type_money") val typeMoney: String? = null,
    @ColumnInfo(name = "price") val price: Double? = null,
    @ColumnInfo(name = "high_price") val highPrice: Double? = null,
    @ColumnInfo(name = "low_price") val lowPrice: Double? = null,
    @ColumnInfo(name = "ask") val ask: Double? = null,
    @ColumnInfo(name = "bind") val bind: Double? = null,
    @ColumnInfo(name = "last_modificaiton") val lastModification: String? = null,
    @ColumnInfo(name = "url_book") val urlBook: String? = null
)

fun TickerUI.tickerResponseToEntity(): TickerEntity =
    TickerEntity(
        fullName = fullName,
        bookName = bookName,
        typeMoney = typeMoney,
        price = price,
        highPrice = highPrice,
        lowPrice = lowPrice,
        ask = ask,
        bind = bind,
        lastModification = lastModification,
        urlBook = urlBook
    )

fun TickerEntity?.tickerEntityToResponse(): TickerUI =
    TickerUI(
        fullName = this?.fullName,
        bookName = this?.bookName,
        typeMoney = this?.typeMoney,
        price = this?.price,
        highPrice = this?.highPrice,
        lowPrice = this?.lowPrice,
        ask = this?.ask,
        bind = this?.bind,
        lastModification = this?.lastModification,
        urlBook = this?.urlBook
    )