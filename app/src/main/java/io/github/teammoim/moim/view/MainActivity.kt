package io.github.teammoim.moim.view

import android.location.Location
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import android.view.View
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.R
import io.github.teammoim.moim.common.FirebaseManager
import io.github.teammoim.moim.view.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider
import com.google.android.gms.location.DetectedActivity
import io.nlopez.smartlocation.*
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence
import io.nlopez.smartlocation.SmartLocation

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OnLocationUpdatedListener, OnActivityUpdatedListener, OnGeofencingTransitionListener {
    override fun onLocationUpdated(p0: Location?) {
        if (p0 != null) {
            toast("update : "+p0.latitude.toString() + " " + p0.longitude.toString())
        }
    }

    override fun onActivityUpdated(p0: DetectedActivity?) {
    }

    override fun onGeofenceTransition(p0: TransitionGeofence?) {
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.timelineMenu ->{
                val fragmentA = TimeLineFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentA).commit()
            }
            R.id.arMenu -> {
                val fragmentB = ARFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentB).commit()
            }
            R.id.settingMenu -> {
                val fragmentC = SettingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentC).commit()
            }
            R.id.friendsMenu -> {
                val fragmentD = FriendsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentD).commit()
            }
            R.id.notificationMenu -> {
                val fragmentF = NotificationFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentF).commit()
            }

        }
        return true
    }

    override fun onStop() {
        super.onStop()
        stopLocation()
    }

    private fun stopLocation() {
        SmartLocation.with(this).location().stop()
        SmartLocation.with(this).activity().stop()
        SmartLocation.with(this).geofencing().stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentA = TimeLineFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container,fragmentA).commit()

        val bottomNavigationView = findViewById<View>(R.id.navigation_view) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        if (FirebaseManager.getUserEmail() == null){
            startActivity(intentFor<SplashActivity>().clearTop().noHistory())
            finish()
        }

        account.setOnClickListener {
            startActivity<ProfileActivity>()
        }

        search_click.setOnClickListener {
            startActivity<SearchActivity>()
        }

        startLocation()
    }

    override fun onBackPressed() {
        alert(getString(R.string.exit), getString(R.string.okay)) {
            yesButton { super.onBackPressed() }
        }.show()
    }

    private fun startLocation() {
        val provider :LocationGooglePlayServicesProvider? = LocationGooglePlayServicesProvider()
        provider?.setCheckLocationSettings(true)
        val smartLocation = SmartLocation.Builder(this).logging(true).build()
        smartLocation.location(provider).start(this)
        smartLocation.activity().start(this)
        val lastLocation = SmartLocation.with(this).location().lastLocation
        if (lastLocation != null) {
            toast(lastLocation.latitude.toString()+" "+lastLocation.longitude.toString())
        }
    }

}



