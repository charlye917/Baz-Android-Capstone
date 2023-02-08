package com.carteagal.baz_android.domain.mapper

import com.carteagal.baz_android.data.model.TickerResponse
import com.carteagal.baz_android.domain.model.TickerUI
import com.carteagal.baz_android.utils.extension.getBookName
import com.carteagal.baz_android.utils.extension.getMoney
import com.carteagal.baz_android.utils.extension.toFormatDate

fun tickerMapper(ticker: TickerResponse): TickerUI =
    TickerUI(
        fullName = ticker.book,
        bookName = ticker.book.getBookName(),
        typeMoney = ticker.book.getMoney(),
        price = ticker.last.toDoubleOrNull() ?: 0.0,
        highPrice = ticker.high.toDoubleOrNull() ?: 0.0,
        lowPrice = ticker.low.toDoubleOrNull() ?: 0.0,
        ask = ticker.ask.toDoubleOrNull() ?: 0.0,
        bind = ticker.bid.toDoubleOrNull() ?: 0.0,
        lastModification = ticker.createdAt.toFormatDate(),
        urlBook = ticker.generateUrlImage()
    )