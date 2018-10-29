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
import com.google.ar.core.ArCoreApk
import android.app.ActivityManager
import android.content.Context
import android.util.Log
import android.view.WindowManager
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import android.content.Intent




class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, OnLocationUpdatedListener, OnActivityUpdatedListener, OnGeofencingTransitionListener {
    private val MIN_OPENGL_VERSION = 3.0
    private var installRequested: Boolean = false
    private var session: Session? = null

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

    override fun onDestroy() {
        super.onDestroy()
        stopLocation()
    }

    private fun stopLocation() {
        SmartLocation.with(this).location().stop()
        SmartLocation.with(this).activity().stop()
        SmartLocation.with(this).geofencing().stop()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) showSystemUI()
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    override fun onResume() {
        super.onResume()

        if (session == null) {
            var exception: Exception? = null
            var message: String? = null
            try {
                when (ArCoreApk.getInstance()?.requestInstall(this, !installRequested)) {
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        installRequested = true
                        return
                    }
                    ArCoreApk.InstallStatus.INSTALLED -> {
                    }
                }

                session = Session(this)

            } catch (e: UnavailableArcoreNotInstalledException) {
                message = "Please install ARCore"
                exception = e
            } catch (e: UnavailableUserDeclinedInstallationException) {
                message = "Please install ARCore"
                exception = e
            } catch (e: UnavailableApkTooOldException) {
                message = "Please update ARCore"
                exception = e
            } catch (e: UnavailableSdkTooOldException) {
                message = "Please update this app"
                exception = e
            } catch (e: UnavailableDeviceNotCompatibleException) {
                message = "This device does not support AR"
                exception = e
            } catch (e: Exception) {
                message = "Failed to create AR session"
                exception = e
            }

            if (message != null) {
                Log.e(TAG, "Exception creating session", exception)
                return
            }
        }

        try {
            session!!.resume()
        } catch (e: CameraNotAvailableException) {
            session = null
            return
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (intent != null) {
            val uri = intent.data
            if (uri != null) {
                val param1: String? = uri.getQueryParameter("param1")
                longToast(param1.toString())
            }
        }

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
        else{
            FirebaseManager.uploadMyInformation()
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


    private fun checkIsSupportedDevice() {
        val openGlVersionString = (this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            longToast("Sceneform requires OpenGL ES 3.0 later")
        } else {
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



