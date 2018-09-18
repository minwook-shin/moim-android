package io.github.teammoim.moim.viewModel

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import io.github.teammoim.moim.App
import io.github.teammoim.moim.common.FirebaseManager
import io.github.teammoim.moim.view.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class IntroViewModel() : ViewModel(), LifecycleObserver {
    fun checkEmail(v : String): Boolean = RegexUtils.isEmail(v)
    fun checkPassword(v : String):Boolean = StringUtils.length(v) > 5
    fun checkEmpty(v: String):Boolean = StringUtils.length(v) == 0

    fun signUp(email : String, password : String){
        FirebaseManager.getEmailSignUp(email,password).addOnCompleteListener{
            if (it.isSuccessful) {
            App.INSTANCE.longToast("회원가입 성공")
            App.INSTANCE.startActivity(App.INSTANCE.intentFor<MainActivity>().newTask())}
        }.addOnCanceledListener {
            App.INSTANCE.longToast("회원가입 실패")
        }.addOnFailureListener {
            App.INSTANCE.longToast("회원가입 실패")
        }
    }

    fun login(email : String, password : String){
        FirebaseManager.getEmailLogIn(email,password).addOnCompleteListener {
            if (it.isSuccessful) {
            App.INSTANCE.longToast("로그인 성공")
            App.INSTANCE.startActivity(App.INSTANCE.intentFor<MainActivity>().newTask())}
        }.addOnCanceledListener {
            App.INSTANCE.longToast("로그인 실패")
        }.addOnFailureListener {
            App.INSTANCE.longToast("로그인 실패")
        }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun resume(){
//    }
}