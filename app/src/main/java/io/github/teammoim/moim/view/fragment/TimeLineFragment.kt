package io.github.teammoim.moim.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.teammoim.moim.App
import io.github.teammoim.moim.base.BaseFragment
import io.github.teammoim.moim.R
import io.github.teammoim.moim.TimelineModel
import io.github.teammoim.moim.adapter.FriendsRecyclerViewAdapter
import io.github.teammoim.moim.adapter.TimelineRecyclerViewAdapter
import io.github.teammoim.moim.common.FirebaseManager
import io.github.teammoim.moim.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_textwriter.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.jetbrains.anko.toast
import android.widget.EditText
import com.blankj.utilcode.util.SnackbarUtils.dismiss
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.teammoim.moim.common.remove
import io.github.teammoim.moim.view.custom.NoResult


class TimeLineFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    val url = "http://m.naver.com"
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline,container,false)
    }
    private var timelineList: ArrayList<TimelineModel> = ArrayList()

    override fun onRefresh() {
        addItem()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe.setOnRefreshListener(this)

        addItem()

        val mLayoutManager = LinearLayoutManager(this.activity)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        timeline_list.layoutManager = mLayoutManager
        timeline_list.adapter = TimelineRecyclerViewAdapter(activity!!.applicationContext, timelineList)
        timeline_list.setHasFixedSize(true)

    }

    fun addItem() {
        timeline_list.invalidate()
        timelineList = App.INSTANCE.timelineArray
        timeline_list.adapter?.notifyDataSetChanged()
        swipe.isRefreshing = false

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.viewTreeObserver.addOnWindowFocusChangeListener {
            if (!App.INSTANCE.timelineArray.isEmpty()){
                noResult.remove()
            }
        }

        if (App.INSTANCE.timelineArray.isEmpty()){
            noResult.addView(NoResult(this.activity!!.applicationContext))
        }
        else{
            noResult.remove()
        }
        super.onViewCreated(view, savedInstanceState)
        connectViewModel()

        fab.setOnClickListener {
            val bottomSheetDialogFragment = TextWriterFragment()
            bottomSheetDialogFragment.show(activity?.supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }



    private fun connectViewModel(){
        viewModel.model.observe(this,Observer<Int> {})
        lifecycle.addObserver(viewModel)
    }
}
