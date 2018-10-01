package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.teammoim.moim.R
import io.github.teammoim.moim.adapter.NotificationRecyclerViewAdapter
import io.github.teammoim.moim.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    private val notificationList: ArrayList<String> = ArrayList()

    override fun onRefresh() {
        addItem()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe.setOnRefreshListener(this)

        addItem()
        notification_list.layoutManager = LinearLayoutManager(activity)
        notification_list.adapter = NotificationRecyclerViewAdapter(activity!!.applicationContext, notificationList)
        notification_list.setHasFixedSize(true)

    }

    private fun addItem() {
        notificationList.clear()
        notification_list.invalidate();
        notificationList.add("회웡가입을 환영합니다.")
        notificationList.add("당신에게 어울리는 새로운 이벤트가 등록되었습니다.")
        notification_list.adapter?.notifyDataSetChanged()
        swipe.isRefreshing = false

    }
}
