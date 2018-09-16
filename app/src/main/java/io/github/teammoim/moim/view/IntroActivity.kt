package io.github.teammoim.moim.view

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.fondesa.kpermissions.extension.listeners
import com.fondesa.kpermissions.extension.permissionsBuilder
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.viewModel.IntroViewModel
import kotlinx.android.synthetic.main.activity_intro.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class IntroActivity : BaseActivity(){
    private val viewModel by lazy { ViewModelProviders.of(this).get(IntroViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        requestPermission()
        clickEvent()
        connectViewModel()
    }

    private fun clickEvent(): Unit {
        joinButton.setOnClickListener {
            if(viewModel.checkEmail(emailText.text.toString())){
                startActivity(intentFor<MainActivity>().clearTop().singleTop())
            }
            joinButton.snackbar("이메일 형식이 아닙니다.")
        }
    }

    private fun connectViewModel(){
        lifecycle.addObserver(viewModel)
    }

    private fun requestPermission(): Unit {
        val request = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE).build()
        request.listeners {
            onAccepted { permissions ->
            }

            onDenied { permissions ->

            }

            onPermanentlyDenied { permissions ->

            }

            onShouldShowRationale { permissions, nonce ->
            }
        }
        request.send()
    }
}