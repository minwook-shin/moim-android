package io.github.teammoim.moim.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.FirebaseManager
import io.github.teammoim.moim.viewModel.SignUpViewModel
import kotlinx.android.synthetic.main.activity_signup_email.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class SignUpEmailActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(SignUpViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_email)

        prevButton.setOnClickListener {
            startActivity(intentFor<SplashActivity>().clearTop().noHistory())
        }

        emailContinueButton.setOnClickListener {
            if (!viewModel.checkEmail(emailText.text.toString())) {
                emailContinueButton.snackbar("이메일이 일치되지 않습니다.")
            }
            else{
                startActivity(intentFor<SignUpPasswordActivity>("email" to emailText.text.toString()).clearTop().noHistory())

            }
        }
    }
}

