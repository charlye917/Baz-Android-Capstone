package com.carteagal.baz_android.utils

import com.carteagal.baz_android.utils.Constants.BASE_IMAGE_URL
import com.carteagal.baz_android.utils.Constants.UNDERSCORE

object BaseUrlImage {
    fun generateUrlImage(book: String) =
        "$BASE_IMAGE_URL/128/color/${book.split(UNDERSCORE)[0]}.png"
}