package io.github.teammoim.moim.viewModel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blankj.utilcode.util.RegexUtils
import io.github.teammoim.moim.App
import org.jetbrains.anko.longToast

class IntroViewModel() : ViewModel(), LifecycleObserver {
    val email : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()
    val nickname : MutableLiveData<String> = MutableLiveData()
    val realName : MutableLiveData<String> = MutableLiveData()

    fun checkEmail(v : String): Boolean {
        if (RegexUtils.isEmail(v)){
            App.INSTANCE.longToast("이메일이 맞습니다!")
            return true
        }else{
            App.INSTANCE.longToast("이메일이 아닙니다!")
            return false
        }
    }

    fun signUp() {

    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun resume(){
//    }
}