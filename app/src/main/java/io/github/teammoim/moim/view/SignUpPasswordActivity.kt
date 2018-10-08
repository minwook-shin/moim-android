package io.github.teammoim.moim.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.NetworkUtils
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.show
import io.github.teammoim.moim.viewModel.SignUpViewModel
import kotlinx.android.synthetic.main.activity_signup_password.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class SignUpPasswordActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(SignUpViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)

        isConnectNetwork()

        val email: String = intent.getStringExtra("email")

        prevButton.setOnClickListener {
            startActivity(intentFor<SignUpEmailActivity>().clearTop().noHistory())
        }

        passwordContinueButton.setOnClickListener {
            if (!viewModel.checkPassword(passwordText.text.toString())) {
                passwordContinueButton.snackbar("비밀번호는 6자리 이상이여야 합니다.")
            } else {
                loading.show()
                val password = passwordText.text.toString()
                viewModel.signUp(email, password, loading)
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