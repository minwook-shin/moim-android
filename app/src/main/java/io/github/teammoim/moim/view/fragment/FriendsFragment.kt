package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.github.teammoim.moim.App
import io.github.teammoim.moim.R
import io.github.teammoim.moim.adapter.FriendsRecyclerViewAdapter
import io.github.teammoim.moim.base.BaseFragment
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.fragment_friends.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.selector
import java.util.*

class FriendsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    private val friendsList: ArrayList<String> = ArrayList()

    override fun onRefresh() {
        App.INSTANCE.myFriend.clear()
        friendsList.clear()
        FirebaseManager.getRef(FirebaseManager.getUserUid())?.child("friend")?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children){
                    App.INSTANCE.myFriend.add(snapshot.value.toString())

                }
                addItem()
            }


        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipe.setOnRefreshListener(this)
        App.INSTANCE.myFriend.add("none")

        onRefresh()
        friends_list.layoutManager = LinearLayoutManager(activity)
        friends_list.adapter = FriendsRecyclerViewAdapter(activity!!.applicationContext, friendsList)
        friends_list.setHasFixedSize(true)

        fab.setOnClickListener {
            val userList = App.INSTANCE.allUser
            activity!!.selector("누구를 친구로 추가하시겠습니까?", userList) { _, i ->
                if (userList[i] == FirebaseManager.getUserEmail()) {
                    fab.snackbar("자기 자신을 친구로 추가할 수 없습니다.")
                } else {
                    val cal = Calendar.getInstance()
                    if (App.INSTANCE.myFriend.contains(userList[i])){
                        fab.snackbar("${userList[i]}님은 이미 친구로 되어있습니다.")
                    }else{
                        fab.snackbar("${userList[i]}님이 친구로 추가되었습니다.")
                        FirebaseManager.getRef(FirebaseManager.getUserUid())?.child("friend")?.child(userList[i].replace(".",""))?.setValue(userList[i])
//                    App.INSTANCE.myFriend.add(countries[i])
                    }


                }

            }
            addItem()
        }

    }

    private fun addItem() {
        friendsList.clear()
        friends_list.invalidate()
        for (i in App.INSTANCE.myFriend) {
            friendsList.add(i)
        }
        friends_list.adapter?.notifyDataSetChanged()
        swipe.isRefreshing = false

    }
}
