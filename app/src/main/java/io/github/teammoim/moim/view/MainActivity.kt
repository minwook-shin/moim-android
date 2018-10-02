package io.github.teammoim.moim.view

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.R
import io.github.teammoim.moim.common.FirebaseManager
import io.github.teammoim.moim.view.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    override fun onBackPressed() {
        alert(getString(R.string.exit), getString(R.string.okay)) {
            yesButton {super.onBackPressed() }
        }.show()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                longToast(task.result.latitude.toString() + "   "+  task.result.longitude.toString() )
            } else {
            }
        }
    }
}



