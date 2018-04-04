package com.kovapss.gitmobile

import android.app.Application
import android.preference.PreferenceManager
import com.kovapss.gitmobile.di.modules.AppModule
import com.kovapss.gitmobile.di.modules.NetworkModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import io.github.kbiakov.codeview.classifier.CodeProcessor
import toothpick.Toothpick

class App : Application() {

    private val tag = "MYTAG"

    override fun onCreate() {
        super.onCreate()
        val formatStrategy = PrettyFormatStrategy.newBuilder().tag(tag).build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        val appScope = Toothpick.openScope(Scopes.APP_SCOPE)
        appScope.installModules(AppModule(this))
        val serverScope = Toothpick.openScope(Scopes.NETWORK_SCOPE)
        serverScope.installModules(NetworkModule())
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
        CodeProcessor.init(this)
    }







}
