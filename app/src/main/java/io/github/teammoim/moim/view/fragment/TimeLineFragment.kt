package io.github.teammoim.moim.view.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import io.github.teammoim.moim.base.BaseFragment
import io.github.teammoim.moim.R
import io.github.teammoim.moim.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimeLineFragment : BaseFragment(){
    val url = "http://m.naver.com"
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectViewModel()
        loadWebView()
        fab.setOnClickListener {
            val bottomSheetDialogFragment = TextWriterFragment()
            bottomSheetDialogFragment.show(activity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(){
        val set = webTimeline.settings
        set.javaScriptEnabled = true
        set.setAppCacheEnabled(true)
        set.cacheMode = WebSettings.LOAD_DEFAULT
        set.setSupportZoom(false)
        set.builtInZoomControls = false
        set.displayZoomControls = false
        set.blockNetworkImage = false
        set.loadsImagesAutomatically = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            set.safeBrowsingEnabled = true  // api 26
        }
        webTimeline.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webTimeline.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            }
            override fun onPageFinished(view: WebView, url: String) {
            }
        }
        webTimeline.loadUrl(url)
    }

    private fun connectViewModel(){
        viewModel.model.observe(this,Observer<Int> {})
        lifecycle.addObserver(viewModel)
    }
}
