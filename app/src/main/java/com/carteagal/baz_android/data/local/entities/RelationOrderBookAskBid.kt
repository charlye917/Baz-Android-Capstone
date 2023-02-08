package com.carteagal.baz_android.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.TypeConverter
import com.carteagal.baz_android.data.model.OrderBookResponse

/*data class RelationOrderBookWithAskBids(
    @Embedded val orderBook: OrderBookEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "order_book_id"
    )
    val ask: List<AskBidsEntity>,
    val bind: List<AskBidsEntity>
)

fun OrderBookResponse.orderBookResponseToEntity(bookName: String): RelationOrderBookWithAskBids =
    RelationOrderBookWithAskBids(
        orderBook = OrderBookEntity(
            bookName = bookName,
            updateAt = updatedAt,
            sequence = sequence
        ),
        ask = asks.askBindsResponseToEntity(),
        bind = binds.askBindsResponseToEntity()
    )

fun RelationOrderBookWithAskBids.orderBookEntityToResponse(): OrderBookResponse =
    OrderBookResponse(
        updatedAt = orderBook.updateAt,
        sequence = orderBook.sequence,
        binds = bind.askBindsEntityToResponse(),
        asks = ask.askBindsEntityToResponse()
    )*/