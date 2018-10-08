package io.github.teammoim.moim.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.NetworkUtils
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class LoginActivity: BaseActivity(){
    private val viewModel by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        isConnectNetwork()

        prevButton.setOnClickListener {
            startActivity(intentFor<SplashActivity>().clearTop().noHistory())
        }

        loginButton.setOnClickListener {
            if (!viewModel.checkEmail(emailText.text.toString())) {
                loginButton.snackbar(getString(R.string.not_valid_email))
            }else if (!viewModel.checkPassword((passwordText.text.toString()))) {
                loginButton.snackbar(getString(R.string.not_valid_password))
            }
            else{
                viewModel.login(emailText.text.toString(),passwordText.text.toString(),loading)
            }
        }
    }

    private fun isConnectNetwork(){
        if (!NetworkUtils.isConnected()){
            alert("네트워크에 연결되지 않았습니다. 와이파이에 연결하시겠습니까?", App.INSTANCE.getString(R.string.okay)) {
                yesButton {
                    NetworkUtils.setWifiEnabled(true)
                }
            }.show()
        }
    }
}