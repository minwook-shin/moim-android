package io.github.teammoim.moim.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
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
import android.widget.EditText
import com.blankj.utilcode.util.SnackbarUtils.dismiss
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.github.teammoim.moim.common.remove
import io.github.teammoim.moim.view.EditInformationActivity
import io.github.teammoim.moim.view.custom.NoResult
import org.jetbrains.anko.*
import java.util.*


class TimeLineFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    val url = "http://m.naver.com"
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    private var timelineList: ArrayList<TimelineModel> = ArrayList()
    val reversed = App.INSTANCE.allUser.entries.associate { (k, v) -> v to k }

    override fun onRefresh() {
        if (!App.INSTANCE.timelineArray.isEmpty()) {
            noResult.remove()
        }
        val cal = Calendar.getInstance()

        App.INSTANCE.myFriend.clear()
        FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid()!!)?.child("subscribe")?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children){
                    App.INSTANCE.myFriend.add(snapshot.value.toString())

                }
                FirebaseManager.getRef("post")?.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        timelineList.clear()
                        App.INSTANCE.timelineArray.clear()
                        App.INSTANCE.myFriend.add(FirebaseManager.getUserEmail().toString())
                        for (snapshot in p0.children) {
                            if (App.INSTANCE.myFriend.contains(App.INSTANCE.allUser[snapshot.key])) {
                                for (snap in snapshot.children) {
                                    val tmp = TimelineModel("","", 0.0, "", "", "")
                                    for (s in snap.children) {
                                        if (s.key == "uid") {
                                            tmp.email = App.INSTANCE.allUser[s.value.toString()].toString()
                                            tmp.name = App.INSTANCE.findName[s.value.toString()].toString()
                                            tmp.uid = s.value.toString()
                                        }
                                        if (s.key == "text") {
                                            tmp.text = s.value.toString()
                                        }
                                        if (s.key == "postId") {
                                            tmp.postId = s.value.toString()
                                        }
                                    }
                                    tmp.date = snap.key.toString().toDouble()
                                    App.INSTANCE.timelineArray.add(tmp)
                                }
                            }
                        }
                        addItem()
                    }
                })
            }
        })


    }

    fun timelineSortUid(p: TimelineModel): Double = p.date.toDouble()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipe.setOnRefreshListener(this)

        onRefresh()
        addItem()

        val mLayoutManager = LinearLayoutManager(this.activity)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        timeline_list.layoutManager = mLayoutManager
        Log.d("timeline",timelineList.toString())
        timeline_list.adapter = TimelineRecyclerViewAdapter(activity!!.applicationContext, timelineList, activity?.supportFragmentManager!!)
        timeline_list.setHasFixedSize(true)

    }

    fun addItem() {
        timelineList = App.INSTANCE.timelineArray
        timelineList.sortBy { timelineSortUid(it)  }
        timeline_list.adapter?.notifyDataSetChanged()
        swipe.isRefreshing = false

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (App.INSTANCE.timelineArray.isEmpty()) {
            noResult.addView(NoResult(this.activity!!.applicationContext))
        } else {
            noResult.remove()
        }
        super.onViewCreated(view, savedInstanceState)
        connectViewModel()

        fab.setOnClickListener {
            if (App.INSTANCE.myInfo.name == ""||App.INSTANCE.myInfo.nickname == ""||App.INSTANCE.myInfo.Email == ""){
                activity?.alert("사용자의 필수 정보가 아직 부족합니다. 추가해주세요.", getString(R.string.okay)) {
                    yesButton {
                        activity?.startActivity<EditInformationActivity>()
                    }
                }?.show()
            }else{
                val bottomSheetDialogFragment = TextWriterFragment()
                bottomSheetDialogFragment.show(activity?.supportFragmentManager, bottomSheetDialogFragment.tag)
            }

        }
    }


    private fun connectViewModel() {
        viewModel.model.observe(this, Observer<Int> {})
        lifecycle.addObserver(viewModel)
    }
}
