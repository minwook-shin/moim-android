package io.github.teammoim.moim.common

import android.app.Activity
import org.jetbrains.anko.longToast

object ExtensionFunction{
    fun Activity.toast(text : String){
        longToast(text)
    }
}