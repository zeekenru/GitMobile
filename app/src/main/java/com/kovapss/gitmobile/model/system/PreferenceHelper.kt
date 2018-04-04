package com.kovapss.gitmobile.model.system

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.kovapss.gitmobile.entities.User
import com.orhanobut.logger.Logger
import javax.inject.Inject


class PreferenceHelper @Inject constructor(context: Context) {

    companion object {
//        private const val SP_NAME = "com.kovapps.gitmobile.sp"

        private const val AUTH_STATUS_KEY = "com.kovapps.gitmobile.sp.auth_status"

        private const val ACCESS_TOKEN_KEY = "com.kovapps.gitmobile.sp.access_token"

        private const val SHOW_LINE_NUMBERS_KEY = "com.kovapps.gitmobile.sp.show_line_numbers"

        private const val USER_LOGIN_KEY = "com.kovapps.gitmobile.sp.user_login"

        private const val CURRENT_USER_KEY = "com.kovapps.gitmobile.sp.current_user_key"

        private const val NONE_USER_LOGIN = "None"

        const val EMPTY_ACCESS_TOKEN = "empty"
    }


    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

//    init {
//          sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
//    }

    fun getAuthStatus(): Boolean = sp.getBoolean(AUTH_STATUS_KEY, false)

    fun setAuthStatus(status: Boolean) = sp.edit().putBoolean(AUTH_STATUS_KEY, status).apply()


    fun setShowLineNumbers(show: Boolean) = sp.edit().putBoolean(SHOW_LINE_NUMBERS_KEY, show).apply()

    fun setCurrentUserLogin(login: String) {
        Logger.d("Setting login : $login")
        sp.edit().putString(USER_LOGIN_KEY, login).apply()
    }

    fun getCurrentUserLogin(): String = sp.getString(USER_LOGIN_KEY, NONE_USER_LOGIN)

    fun getShowLineNumbers(): Boolean = sp.getBoolean(SHOW_LINE_NUMBERS_KEY, false)

    fun getAccessToken(): String = sp.getString(ACCESS_TOKEN_KEY, EMPTY_ACCESS_TOKEN)

    fun setAccessToken(token: String) {
        Logger.d("Setting token : $token")
        sp.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }


}