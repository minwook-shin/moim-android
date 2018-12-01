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

    var myInfo = MyInformationModel("","","","","","","")
    val timelineArray = ArrayList<TimelineModel>()
    val allUser = mutableMapOf<String?,String>()
    val myFriend = ArrayList<String>()
    val geoPoint = ArrayList<LocationModel>()
    val distanceMap = mutableMapOf<Double,LocationModel>()


    var myLatitude : Double = 0.0
    var myLongitude : Double = 0.0

    companion object {
        lateinit var INSTANCE: App
        lateinit var prefs: AppSharedPreferences
    }
}