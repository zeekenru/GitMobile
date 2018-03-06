package com.kovapss.gitmobile.di.modules

import android.content.Context
import com.kovapss.gitmobile.model.db.AppDatabase
import com.kovapss.gitmobile.di.providers.DatabaseProvider
import com.kovapss.gitmobile.model.system.PreferenceHelper
import com.kovapss.gitmobile.model.system.ResourceHelper
import com.orhanobut.logger.Logger
import toothpick.config.Module


class AppModule(context: Context) : Module(){

    init {
        Logger.d("App module init")

        bind(Context::class.java).toInstance(context)

        bind(PreferenceHelper::class.java).singletonInScope()

        bind(AppDatabase::class.java).toProviderInstance(DatabaseProvider(context))

        bind(ResourceHelper::class.java).toInstance(ResourceHelper(context))
    }
}


