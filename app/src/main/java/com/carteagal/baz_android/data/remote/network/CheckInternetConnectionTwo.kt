package com.carteagal.baz_android.data.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object CheckInternetConnection {
    fun hasInternetConnection(context: Context?): Boolean{
        if(context == null) return false

        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectionManager.activeNetwork ?: return false
        val networkCapabilities = connectionManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when{
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}