package io.github.teammoim.moim.view

import android.Manifest
import android.os.Bundle
import com.fondesa.kpermissions.extension.listeners
import com.fondesa.kpermissions.extension.permissionsBuilder
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.noHistory

class SplashActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loginButton.setOnClickListener {
            startActivity(intentFor<LoginActivity>().clearTop().noHistory())
        }
        signUpButton.setOnClickListener {
            startActivity(intentFor<SignUpEmailActivity>().clearTop().noHistory())
        }
    }

    private fun requestPermission(): Unit {
        val request = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE).build()
        request.send()
        request.listeners {
            onAccepted { permissions ->
                longToast(getString(R.string.all_permission_okay))
            }

            onDenied { permissions ->
                longToast(getString(R.string.all_permission_deny))
                val reRequest = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE).build()
                reRequest.send()
            }

            onPermanentlyDenied { permissions ->
                longToast(permissions.toString())
                val reRequest = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE).build()
                reRequest.send()
            }

            onShouldShowRationale { permissions, nonce ->
            }
        }
    }
}