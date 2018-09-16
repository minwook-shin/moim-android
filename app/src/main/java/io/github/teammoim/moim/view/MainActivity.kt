package io.github.teammoim.moim.view

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import com.fondesa.kpermissions.extension.permissionsBuilder
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.R
import io.github.teammoim.moim.view.fragment.TimeLineFragment
import io.github.teammoim.moim.view.fragment.ARFragment
import io.github.teammoim.moim.view.fragment.SettingFragment

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
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

        // Creates the request with the permissions you would like to request.
        val request = permissionsBuilder(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE).build()
        // Send the request when you want.
        request.send()
    }

}



