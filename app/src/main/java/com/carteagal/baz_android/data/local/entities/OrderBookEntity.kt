package com.carteagal.baz_android.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.carteagal.baz_android.data.model.OrderBookResponse

@Entity(tableName = "order_book_table")
data class OrderBookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "book_name") val bookName: String?,
    @ColumnInfo(name = "update_at") val updateAt: String?,
    @ColumnInfo(name = "sequence") val sequence: String?
    //@ColumnInfo(name = "bids") val bids: List<AskBidsEntity> = arrayListOf(),
    //@ColumnInfo(name = "ask") val ask: List<AskBidsEntity> = arrayListOf()
)

/*fun OrderBookResponse.orderBookResponseToEntity(bookName: String): OrderBookEntity =
    OrderBookEntity(
        bookName = bookName,
        updateAt = updatedAt,
        sequence = sequence
        //bids = bids!!.askBindsResponseToEntity(),
        //ask = asks!!.askBindsResponseToEntity()
    )
@TypeConverter
fun OrderBookEntity.orderBookEntityToResponse(): OrderBookResponse =
    OrderBookResponse(
        updatedAt = updateAt,
        sequence = sequence
        //bids = bids.askBindsEntityToResponse(),
        //asks = ask.askBindsEntityToResponse()
    )*/





