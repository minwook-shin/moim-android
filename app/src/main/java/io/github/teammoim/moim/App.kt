package io.github.teammoim.moim

import android.app.Application
import android.content.Intent
import io.github.teammoim.moim.common.AppSharedPreferences
import org.osmdroid.util.GeoPoint

class App : Application(){
    init {
        INSTANCE = this
    }

    override fun onCreate() {
        startService(Intent(INSTANCE.applicationContext, AppService::class.java))
        prefs = AppSharedPreferences(applicationContext)
        super.onCreate()
    }

    var myInfo = MyInformationModel("","")
    val timelineArray = ArrayList<TimelineModel>()
    val allUser = mutableMapOf<String?,String>()
    val myFriend = ArrayList<String>()
    val geoPoint = ArrayList<GeoPoint>()



    companion object {
        lateinit var INSTANCE: App
        lateinit var prefs: AppSharedPreferences
    }
}