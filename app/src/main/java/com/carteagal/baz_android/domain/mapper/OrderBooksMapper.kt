package com.carteagal.baz_android.domain.mapper

import com.carteagal.baz_android.data.model.OrderBookResponse
import com.carteagal.baz_android.domain.model.BidsAskUI
import com.carteagal.baz_android.domain.model.OrderBookUI

/*fun orderBooksMapper(orderBook: OrderBookResponse): OrderBookUI =
    OrderBookUI(
        bids = orderBook.binds.map {
            BidsAskUI(
                price = it.price!!,
                amount = it.amount!!
            )
        },
        ask = orderBook.asks.map {
            BidsAskUI(
                price = it.price!!,
                amount = it.amount!!
            )
        }
    )*/