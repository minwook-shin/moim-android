package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseFragment
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logoutButton.setOnClickListener {
            FirebaseManager.signOut()
            activity?.onBackPressed()
        }
    }
}
