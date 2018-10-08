package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.CleanUtils
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseFragment
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.design.snackbar
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import io.github.teammoim.moim.App
import io.github.teammoim.moim.view.SplashActivity
import org.jetbrains.anko.*


class SettingFragment : BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logoutButton.setOnClickListener {
            activity?.alert("정말로 로그아웃 하시겠습니까?", getString(R.string.okay)) {
                yesButton {
                    FirebaseManager.signOut()
                    App.INSTANCE.startActivity(App.INSTANCE.intentFor<SplashActivity>().clearTask().noHistory().newTask())
                }
            }?.show()
        }
        cleanButton.setOnClickListener {
            CleanUtils.cleanExternalCache()
            CleanUtils.cleanInternalCache()
            cleanButton.snackbar("성공적으로 앱의 임시 파일을 제거했습니다.")
        }
        ossButton.setOnClickListener {
            LibsBuilder()
                    .withAutoDetect(true)
                    .withLicenseShown(true)
                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                    .withAboutIconShown(true)
                    .withAboutAppName("MOIM")
                    .start(this.activity)
        }
    }
}
