package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseFragment

class ARcameraFragment: BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ar_camera,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.decorView?.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
    }
}