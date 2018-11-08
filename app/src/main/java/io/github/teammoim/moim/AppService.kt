package io.github.teammoim.moim

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.github.teammoim.moim.common.FirebaseManager
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class AppService : Service() {
    override fun onBind(p0: Intent?): IBinder =
            throw UnsupportedOperationException(getString(R.string.not_implemented))

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        myInformation()
        return Service.START_STICKY
    }


    private fun myInformation() {
        FirebaseManager.getRef(FirebaseManager.getUserUid())?.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                if (p0.key.equals("email"))
                    App.INSTANCE.myInfo.Email = p0.value.toString()

                if(p0.key.equals("friend")){
                    for(snapshot in p0.children){
                        App.INSTANCE.myFriend.add(snapshot.value.toString())
                    }
                }

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                if (p0.key.equals("email"))
                    App.INSTANCE.myInfo.Email = p0.value.toString()
            }

        })

        FirebaseManager.getRef("userList")?.addChildEventListener(object:ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                App.INSTANCE.allUser.add(p0.value.toString())
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                App.INSTANCE.allUser.add(p0.value.toString())
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })


    }

}