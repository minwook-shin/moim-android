package io.github.teammoim.moim.view

import android.os.Bundle
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.noHistory

class LoginActivity: BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prevButton.setOnClickListener {
            startActivity(intentFor<SplashActivity>().clearTop().noHistory())
        }
    }
}