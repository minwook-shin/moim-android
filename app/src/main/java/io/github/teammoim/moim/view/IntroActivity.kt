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
                    joinButton.snackbar("이메일 형식이 아닙니다.")
                }else if (!viewModel.checkPassword((passwordText.text.toString()))) {
                    joinButton.snackbar("비밀번호는 6자리 이상 입력해야 합니다.")
                }
                else if (viewModel.checkEmpty((nameText.text.toString()))) {
                    joinButton.snackbar("이름은 비워둘 수 없습니다.")
                }
                else if (viewModel.checkEmpty((nickNameText.text.toString()))) {
                    joinButton.snackbar("닉네임은 비워둘 수 없습니다.")
                }else {
                    viewModel.signUp(emailText.text.toString(),passwordText.text.toString())
                    finish()
                }
            }
            else if (!signUpMode){
                nicknameBox.show()
                nameBox.show()
                signUpMode = true
                introText.text = "안녕하세요. MOIM에 처음 오셨나요?"
            }


        }

        loginButton.setOnClickListener {
            if (signUpMode){
                nicknameBox.remove()
                nameBox.remove()
                signUpMode = false
                introText.text = "MOIM에 다시 방문하셨군요."
            }
            else{
                if (!viewModel.checkEmail(emailText.text.toString())) {
                    loginButton.snackbar("이메일 형식이 아닙니다.")
                }else if (!viewModel.checkPassword((passwordText.text.toString()))) {
                    loginButton.snackbar("비밀번호는 6자리 이상 입력해야 합니다.")
                }
                else{
                    viewModel.login(emailText.text.toString(),passwordText.text.toString())
                    finish()
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
                longToast("모든 권한이 승인되었습니다.")
            }

            onDenied { permissions ->
                longToast("모든 권한이 거부되었습니다.")
                val reRequest = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE).build()
                reRequest.send()
            }

            onPermanentlyDenied { permissions ->
                longToast(permissions.toString() + "일부 권한이 거부되었습니다.")
                val reRequest = permissionsBuilder(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE).build()
                reRequest.send()
            }

            onShouldShowRationale { permissions, nonce ->
            }
        }
    }

    override fun onBackPressed() {
        alert("정말로 종료하시겠습니까?", "확인") {
            yesButton {
                super.onBackPressed()
            }
        }.show()
    }
}