package com.carteagal.baz_android.utils.extension

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import com.carteagal.baz_android.utils.Constants.FORMAT_DATE_WITH_TIME
import com.carteagal.baz_android.utils.Constants.FORMAT_DATE_WITH_TIME_PATTERN
import com.carteagal.baz_android.utils.Constants.UNDERSCORE
import com.carteagal.baz_android.utils.NameCrypto
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Double.toAmountFormat(): String =
    NumberFormat
        .getCurrencyInstance(Locale.US)
        .format(this)

@SuppressLint("SimpleDateFormat")
fun String.toFormatDate(): String {
    val date = SimpleDateFormat(FORMAT_DATE_WITH_TIME_PATTERN, Locale.ROOT).parse(this)!!
    val dateFormat = SimpleDateFormat(FORMAT_DATE_WITH_TIME)
    return dateFormat.format(date)
}

fun String.separeteUnderscore() =
    this.split(UNDERSCORE)[0]

fun String.getAbbreviation() =
    this.split(UNDERSCORE)[0].uppercase()

fun String.getBookName() =
    NameCrypto.getFullName(this.split(UNDERSCORE)[0])

fun String.getMoney() =
    this.split(UNDERSCORE)[1].uppercase()

