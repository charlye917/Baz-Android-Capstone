package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carteagal.baz_android.domain.model.AskBindUI
import com.carteagal.baz_android.utils.TypeAskBid

@Entity(tableName = "ask_bind_table")
data class AskBindEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book") val book: String,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "type") val type: TypeAskBid,
)

fun List<AskBindUI>.askBindUItoEntity(): List<AskBindEntity> =
    map {
        AskBindEntity(
            book = it.book,
            amount = it.amount,
            price = it.price,
            type = it.type
        )
    }

fun List<AskBindEntity>.askBindEntityToUI(): List<AskBindUI> =
    map {
        AskBindUI(
            book = it.book,
            amount = it.amount,
            price = it.price,
            type = it.type
        )
    }





