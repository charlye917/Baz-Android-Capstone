package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carteagal.baz_android.data.model.AvailableBookResponse

@Entity(tableName = "available_book_table")
data class AvailableBookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_name") val bookName: String? = null,
    @ColumnInfo(name = "minimum_price") val minimumPrice: String? = null,
    @ColumnInfo(name = "maximum_price") val maximumPrice: String? = null,
    @ColumnInfo(name = "minimum_amount") val minimunAmount: String? = null,
    @ColumnInfo(name = "maximum_amount") val maximumAmount: String? = null,
    @ColumnInfo(name = "minimum_value") val minimumValue: String? = null,
    @ColumnInfo(name = "maximum_value") val maximumValue: String? = null,
    @ColumnInfo(name = "tick_size") val ticketSize: String? = null
)

fun List<AvailableBookResponse>.availableResponcesToEntity(): List<AvailableBookEntity> =
    map {
        AvailableBookEntity(
            bookName = it.book,
            minimumPrice = it.minimumPrice,
            maximumPrice =  it.maximumPrice,
            minimunAmount = it.minimumAmount,
            maximumAmount = it.maximumAmount,
            minimumValue = it.minimumValue,
            maximumValue = it.maximumValue,
            ticketSize = it.tickSize
        )
    }

fun List<AvailableBookEntity>.availableEntitysToResponse(): List<AvailableBookResponse> =
    map {
        AvailableBookResponse(
            book = it.bookName,
            minimumPrice = it.minimumPrice,
            maximumPrice =  it.maximumPrice,
            minimumAmount = it.minimunAmount,
            maximumAmount = it.maximumAmount,
            minimumValue = it.minimumValue,
            maximumValue = it.maximumValue,
            tickSize = it.ticketSize
        )
    }
