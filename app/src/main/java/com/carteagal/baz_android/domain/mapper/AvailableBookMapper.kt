package com.carteagal.baz_android.domain.mapper

import com.carteagal.baz_android.data.remote.model.AvailableBookResponse
import com.carteagal.baz_android.domain.model.AvailableBookUI
import com.carteagal.baz_android.utils.extension.getBookName
import com.carteagal.baz_android.utils.extension.getMoney

fun availableMapper(list: List<AvailableBookResponse>): List<AvailableBookUI> =
    list.map {
        AvailableBookUI(
            fullName = it.book,
            name = it.book!!.getBookName(),
            typeMoney = it.book.getMoney(),
            imageUrl = it.generateUrlImage(),
            maxPrice = it.maximumPrice?.toDoubleOrNull() ?: 0.0,
            minPrice = it.minimumPrice?.toDoubleOrNull() ?: 0.0,
            amount = it.maximumAmount?.toDoubleOrNull() ?: 0.0
        )
    }.sortedBy { it.name }
