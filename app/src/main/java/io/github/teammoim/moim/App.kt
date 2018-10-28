package io.github.teammoim.moim

import android.app.Application
import android.content.Intent
import io.github.teammoim.moim.common.AppSharedPreferences
import io.github.teammoim.moim.common.FirebaseManager

class App : Application(){
    init {
        INSTANCE = this
    }

    override fun onCreate() {
        startService(Intent(INSTANCE.applicationContext, AppService::class.java))
        prefs = AppSharedPreferences(applicationContext)
        super.onCreate()
    }

    var myInfo = myInformationModel("","")
    val timelineArray = ArrayList<TimelineModel>()

    companion object {
        lateinit var INSTANCE: App
        lateinit var prefs: AppSharedPreferences
    }
}