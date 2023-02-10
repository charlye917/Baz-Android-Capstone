package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.domain.model.AvailableBookUI

@Entity(tableName = "available_book_table")
data class AvailableBookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "full_name") val fullName: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "type_money") val typeMoney: String? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "max_price") val maxPrice: Double? = null,
    @ColumnInfo(name = "min_price") val minPrice: Double? = null,
    @ColumnInfo(name = "amount") val amount: Double? = null
)

fun List<AvailableBookUI>.availableUIToEntity(): List<AvailableBookEntity> =
    map {
        AvailableBookEntity(
            fullName = it.fullName,
            name = it.name,
            typeMoney =  it.typeMoney,
            imageUrl = it.imageUrl,
            maxPrice = it.maxPrice,
            minPrice = it.minPrice,
            amount = it.amount
        )
    }

fun List<AvailableBookEntity>.availableEntitysToUI(): List<AvailableBookUI> =
    map {
        AvailableBookUI(
            fullName = it.fullName,
            name = it.name,
            typeMoney =  it.typeMoney,
            imageUrl = it.imageUrl,
            maxPrice = it.maxPrice,
            minPrice = it.minPrice,
            amount = it.amount
        )
    }
