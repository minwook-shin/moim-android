package io.github.teammoim.moim.view

import android.os.Bundle
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        prevButton.setOnClickListener {
            this.onBackPressed()
        }
    }
}