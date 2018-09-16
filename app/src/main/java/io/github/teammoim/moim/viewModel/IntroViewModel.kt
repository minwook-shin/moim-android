package io.github.teammoim.moim.viewModel

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blankj.utilcode.util.RegexUtils
import io.github.teammoim.moim.App
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.longToast

class IntroViewModel() : ViewModel(), LifecycleObserver {
    val email : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()
    val nickname : MutableLiveData<String> = MutableLiveData()
    val realName : MutableLiveData<String> = MutableLiveData()

    fun checkEmail(v : String): Boolean = RegexUtils.isEmail(v)

    fun signUp() {

    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun resume(){
//    }
}