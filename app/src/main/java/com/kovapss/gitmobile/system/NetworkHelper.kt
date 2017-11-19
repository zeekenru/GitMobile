package com.kovapss.gitmobile.system

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class NetworkHelper @Inject constructor(private val context: Context) {


    fun isConnected(): Boolean {
        val manager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = manager.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}