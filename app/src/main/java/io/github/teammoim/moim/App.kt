package io.github.teammoim.moim

import android.app.Application
import android.content.Intent
import io.github.teammoim.moim.common.AppSharedPreferences

class App : Application(){
    init {
        INSTANCE = this
    }

    override fun onCreate() {
        startService(Intent(INSTANCE.applicationContext, AppService::class.java))
        prefs = AppSharedPreferences(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var INSTANCE: App
        lateinit var prefs: AppSharedPreferences
    }
}