package io.github.teammoim.moim.viewModel

import android.widget.ProgressBar
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.StringUtils
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.common.FirebaseManager
import io.github.teammoim.moim.common.show
import io.github.teammoim.moim.view.MainActivity
import org.jetbrains.anko.*

class LoginViewModel() : ViewModel(), LifecycleObserver {
    fun checkEmail(v : String): Boolean = RegexUtils.isEmail(v)
    fun checkPassword(v : String):Boolean = StringUtils.length(v) > 5

    fun login(email: String, password: String, loading: ProgressBar){
        FirebaseManager.getEmailLogIn(email,password).addOnCompleteListener {
            loading.show()
            if (it.isSuccessful) {
            App.INSTANCE.longToast(App.INSTANCE.getString(R.string.login_success))
            App.INSTANCE.startActivity(App.INSTANCE.intentFor<MainActivity>().newTask())}
        }.addOnCanceledListener {
            App.INSTANCE.longToast(App.INSTANCE.getString(R.string.login_fail))
        }.addOnFailureListener {
            App.INSTANCE.longToast(App.INSTANCE.getString(R.string.login_fail))
        }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun resume(){
//    }
}