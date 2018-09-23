package io.github.teammoim.moim.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseView

class Logout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseView(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.custom_logout_button, this, true)
    }
}