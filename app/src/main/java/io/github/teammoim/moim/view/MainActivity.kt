package io.github.teammoim.moim.view

import android.location.Location
import android.os.Bundle
import android.os.Handler
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
import androidx.core.os.HandlerCompat.postDelayed
import com.google.ar.core.ArCoreApk
import android.widget.Toast
import com.google.a.b.a.a.a.e
import android.content.Context.ACTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.ActivityManager
import android.os.Build.VERSION_CODES
import android.os.Build
import android.app.Activity
import android.content.Context
import android.util.Log


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OnLocationUpdatedListener, OnActivityUpdatedListener, OnGeofencingTransitionListener {
    private val MIN_OPENGL_VERSION = 3.0
    override fun onLocationUpdated(p0: Location?) {
        if (p0 != null) {
            toast("update : " + p0.latitude.toString() + " " + p0.longitude.toString())
        }
    }

    override fun onActivityUpdated(p0: DetectedActivity?) {
    }

    override fun onGeofenceTransition(p0: TransitionGeofence?) {
    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.timelineMenu -> {
                val fragmentA = TimeLineFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentA).commit()
                camera_fab.hide()
            }
            R.id.arMenu -> {
                val fragmentB = ARFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentB).commit()
                camera_fab.show()
            }
            R.id.settingMenu -> {
                val fragmentC = SettingFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentC).commit()
                camera_fab.hide()
            }
            R.id.friendsMenu -> {
                val fragmentD = FriendsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentD).commit()
                camera_fab.hide()
            }
            R.id.notificationMenu -> {
                val fragmentF = NotificationFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentF).commit()
                camera_fab.hide()
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
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentA).commit()
        camera_fab.hide()

        maybeEnableArButton()

        val bottomNavigationView = findViewById<View>(R.id.navigation_view) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        if (FirebaseManager.getUserEmail() == null) {
            startActivity(intentFor<SplashActivity>().clearTop().noHistory())
            finish()
        }

        account.setOnClickListener {
            startActivity<ProfileActivity>()
        }

        search_click.setOnClickListener {
            startActivity<SearchActivity>()
        }

        camera_fab.setOnClickListener {
            checkIsSupportedDevice()
            camera_fab.hide()
        }
        startLocation()
    }

    override fun onBackPressed() {
        alert(getString(R.string.exit), getString(R.string.okay)) {
            yesButton { super.onBackPressed() }
        }.show()
    }

    private fun checkIsSupportedDevice() {
        val openGlVersionString = (this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            longToast("Sceneform requires OpenGL ES 3.0 later")
        }
        else{
            val cameraFragment = ARcameraFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, cameraFragment).commit()
        }
    }

    private fun startLocation() {
        val provider: LocationGooglePlayServicesProvider? = LocationGooglePlayServicesProvider()
        provider?.setCheckLocationSettings(true)
        val smartLocation = SmartLocation.Builder(this).logging(true).build()
        smartLocation.location(provider).start(this)
        smartLocation.activity().start(this)
        val lastLocation = SmartLocation.with(this).location().lastLocation
        if (lastLocation != null) {
            toast(lastLocation.latitude.toString() + " " + lastLocation.longitude.toString())
        }
    }

    private fun maybeEnableArButton() {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {
            Handler().postDelayed({
                maybeEnableArButton()
            }, 200)
        }
        camera_fab.isEnabled = availability.isSupported
    }

}



