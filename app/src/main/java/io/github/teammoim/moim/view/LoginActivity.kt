package io.github.teammoim.moim.view

import android.os.Bundle
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class LoginActivity: BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prevButton.setOnClickListener {
            startActivity(intentFor<SplashActivity>().clearTop().noHistory())
        }

        loginButton.setOnClickListener {
            if (!checkEmail(emailText.text.toString())) {
                loginButton.snackbar(getString(R.string.not_valid_email))
            }else if (!checkPassword((passwordText.text.toString()))) {
                loginButton.snackbar(getString(R.string.not_valid_password))
            }
            else{
                login(emailText.text.toString(),passwordText.text.toString())
            }
        }
    }

    private fun login(email : String, password : String){
        FirebaseManager.getEmailLogIn(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
                App.INSTANCE.longToast(App.INSTANCE.getString(R.string.login_success))
                App.INSTANCE.startActivity(App.INSTANCE.intentFor<MainActivity>().newTask())}
        }.addOnCanceledListener {
            App.INSTANCE.longToast(App.INSTANCE.getString(R.string.login_fail))
        }.addOnFailureListener {
            App.INSTANCE.longToast(App.INSTANCE.getString(R.string.login_fail))
        }
    }
    private fun checkEmail(v : String): Boolean = RegexUtils.isEmail(v)
    private fun checkPassword(v : String):Boolean = StringUtils.length(v) > 5
}