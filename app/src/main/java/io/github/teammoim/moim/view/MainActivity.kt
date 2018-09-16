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
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

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
    }

    override fun onBackPressed() {
        alert("정말로 종료하시겠습니까?", "확인") {
            yesButton { finishAffinity()
                System.runFinalization()
                System.exit(0) }
        }.show()
    }
}



