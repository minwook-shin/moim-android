package io.github.teammoim.moim.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){
    val TAG = this.javaClass.simpleName
}