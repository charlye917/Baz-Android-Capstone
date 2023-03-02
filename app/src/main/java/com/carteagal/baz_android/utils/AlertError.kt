package com.carteagal.baz_android.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.carteagal.baz_android.R

object AlertError {
    fun toastInternet(context: Context){
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.message_internet_connection_off, null)
        val toast = Toast(context)
        toast.view = view
        toast.show()
    }
}