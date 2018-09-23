package io.github.teammoim.moim.view

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.fondesa.kpermissions.extension.listeners
import com.fondesa.kpermissions.extension.permissionsBuilder
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.hide
import io.github.teammoim.moim.common.remove
import io.github.teammoim.moim.common.show
import io.github.teammoim.moim.common.toggleVisibility
import io.github.teammoim.moim.viewModel.IntroViewModel
import kotlinx.android.synthetic.main.activity_intro.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class IntroActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(IntroViewModel::class.java) }

    var signUpMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        requestPermission()
        clickEvent()
        connectViewModel()
    }


    private fun clickEvent(): Unit {
        joinButton.setOnClickListener {
            if (signUpMode){
                if (!viewModel.checkEmail(emailText.text.toString())) {
                    joinButton.snackbar(getString(R.string.not_valid_email))
                }else if (!viewModel.checkPassword((passwordText.text.toString()))) {
                    joinButton.snackbar(getString(R.string.not_valid_password))
                }
                else if (viewModel.checkEmpty((nameText.text.toString()))) {
                    joinButton.snackbar(getString(R.string.not_valid_name))
                }
                else if (viewModel.checkEmpty((nickNameText.text.toString()))) {
                    joinButton.snackbar(getString(R.string.not_valid_nickname))
                }else {
                    viewModel.signUp(emailText.text.toString(),passwordText.text.toString())
                }
            }
            else if (!signUpMode){
                nicknameBox.show()
                nameBox.show()
                signUpMode = true
                introText.text = getString(R.string.wellcome)
            }


        }

        loginButton.setOnClickListener {
            if (signUpMode){
                nicknameBox.remove()
                nameBox.remove()
                signUpMode = false
                introText.text = getString(R.string.visit)
            }
            else{
                if (!viewModel.checkEmail(emailText.text.toString())) {
                    loginButton.snackbar(getString(R.string.not_valid_email))
                }else if (!viewModel.checkPassword((passwordText.text.toString()))) {
                    loginButton.snackbar(getString(R.string.not_valid_password))
                }
                else{
                    viewModel.login(emailText.text.toString(),passwordText.text.toString())
                }
            }
        }
    }

    private fun connectViewModel() {
        lifecycle.addObserver(viewModel)
    }

    private fun requestPermission(): Unit {
        val request = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE).build()
        request.send()
        request.listeners {
            onAccepted { permissions ->
                longToast(getString(R.string.all_permission_okay))
            }

            onDenied { permissions ->
                longToast(getString(R.string.all_permission_deny))
                val reRequest = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE).build()
                reRequest.send()
            }

            onPermanentlyDenied { permissions ->
                longToast(permissions.toString())
                val reRequest = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE).build()
                reRequest.send()
            }

            onShouldShowRationale { permissions, nonce ->
            }
        }
    }

    override fun onBackPressed() {
        alert(getString(R.string.exit), getString(R.string.okay)) {
            yesButton {
                super.onBackPressed()
            }
        }.show()
    }
}