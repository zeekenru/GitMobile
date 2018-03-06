package com.kovapss.gitmobile.exceptions

import com.orhanobut.logger.Logger


class NoNetworkException : Exception(){
    init {
        Logger.d("Throwing NoNetworkExc")
    }
}