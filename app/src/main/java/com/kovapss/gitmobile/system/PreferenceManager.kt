package com.kovapss.gitmobile.system

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.orhanobut.logger.Logger
import javax.inject.Inject


class PreferenceManager @Inject constructor(private val context: Context) {

    companion object {
        private const val SP_NAME = "com.kovapps.gitmobile.sp"

        private const val AUTH_STATUS_KEY = "com.kovapps.gitmobile.sp.auth_status"

        private const val ACCESS_TOKEN_KEY = "com.kovapps.gitmobile.sp.access_token"

        const val EMPTY_ACCESS_TOKEN = "empty"
    }


    private val sp: SharedPreferences

    init {
        sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
    }

    fun getAuthStatus(): Boolean = sp.getBoolean(AUTH_STATUS_KEY, false)

    fun setAuthStatus(status: Boolean) {
        sp.edit().putBoolean(AUTH_STATUS_KEY, status).apply()
    }

    fun getAccessToken() : String = sp.getString(ACCESS_TOKEN_KEY, EMPTY_ACCESS_TOKEN)

    fun setAccessToken(token : String) {
        Logger.d("Setting token : $token")
        sp.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }


}