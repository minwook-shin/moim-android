package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.teammoim.moim.R
import io.github.teammoim.moim.adapter.FriendsRecyclerViewAdapter
import io.github.teammoim.moim.adapter.NotificationRecyclerViewAdapter
import io.github.teammoim.moim.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends,container,false)
    }

    private val friendsList: ArrayList<String> = ArrayList()

    override fun onRefresh() {
        addItem()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe.setOnRefreshListener(this)

        addItem()
        friends_list.layoutManager = LinearLayoutManager(activity)
        friends_list.adapter = FriendsRecyclerViewAdapter(activity!!.applicationContext, friendsList)
        friends_list.setHasFixedSize(true)

    }

    private fun addItem() {
        friendsList.clear()
        friends_list.invalidate();
        friendsList.add("한OO")
        friendsList.add("신OO")
        friendsList.add("최OO")
        friendsList.add("최OO")
        friendsList.add("안OO")
        friendsList.add("안OO")
        friendsList.add("한OO")
        friendsList.add("신OO")
        friendsList.add("최OO")
        friendsList.add("최OO")
        friendsList.add("안OO")
        friendsList.add("안OO")
        friends_list.adapter?.notifyDataSetChanged()
        swipe.isRefreshing = false

    }
}
