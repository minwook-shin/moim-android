package io.github.teammoim.moim.base

import android.annotation.SuppressLint
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){
    val TAG = this.javaClass.simpleName

    override fun setContentView(view: View?) {
        super.setContentView(view)
        this?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}