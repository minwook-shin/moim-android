package io.github.teammoim.moim

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.github.teammoim.moim.common.FirebaseManager
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.osmdroid.util.GeoPoint

class AppService : Service() {
    override fun onBind(p0: Intent?): IBinder =
            throw UnsupportedOperationException(getString(R.string.not_implemented))

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        myInformation()
        return Service.START_STICKY
    }


    private fun myInformation() {
        FirebaseManager.getRef("users")?.child(FirebaseManager.getUserUid().toString())?.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                if (p0.key.equals("email"))
                    App.INSTANCE.myInfo.Email = p0.value.toString()
                if (p0.key.equals("birthday"))
                    App.INSTANCE.myInfo.birthday = p0.value.toString()
                if (p0.key.equals("gender"))
                    App.INSTANCE.myInfo.gender = p0.value.toString()
                if (p0.key.equals("name"))
                    App.INSTANCE.myInfo.name = p0.value.toString()
                if (p0.key.equals("nickname"))
                    App.INSTANCE.myInfo.nickname = p0.value.toString()
                if (p0.key.equals("phone"))
                    App.INSTANCE.myInfo.phone = p0.value.toString()

                if (p0.key.equals("friend")) {
                    for (snapshot in p0.children) {
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
                if (p0.key.equals("birthday"))
                    App.INSTANCE.myInfo.birthday = p0.value.toString()
                if (p0.key.equals("gender"))
                    App.INSTANCE.myInfo.gender = p0.value.toString()
                if (p0.key.equals("name"))
                    App.INSTANCE.myInfo.name = p0.value.toString()
                if (p0.key.equals("nickname"))
                    App.INSTANCE.myInfo.nickname = p0.value.toString()
                if (p0.key.equals("phone"))
                    App.INSTANCE.myInfo.phone = p0.value.toString()
                if (p0.key.equals("friend")) {
                    for (snapshot in p0.children) {
                        App.INSTANCE.myFriend.add(snapshot.value.toString())
                    }
                }
            }

        })

        FirebaseManager.getRef("users")?.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                for (snapshot in p0.children) {
                    if (snapshot.key == "email") {
                        App.INSTANCE.allUser[p0.key.toString()] = snapshot.value.toString()
                    }
                }

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                for (snapshot in p0.children) {
                    if (snapshot.key == "email") {
                        App.INSTANCE.allUser[p0.key.toString()] = snapshot.value.toString()
                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
        FirebaseManager.getRef("users")?.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                for (snapshot in p0.children) {
                    if (snapshot.key == "name") {
                        App.INSTANCE.findName[p0.key.toString()] = snapshot.value.toString()
                    }
                }

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                for (snapshot in p0.children) {
                    if (snapshot.key == "name") {
                        App.INSTANCE.findName[p0.key.toString()] = snapshot.value.toString()
                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })

        FirebaseManager.getRef("events")?.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val tmp = LocationModel(GeoPoint(0.0, 0.0), "", "", "", "")
                for (snapshot in p0.children) {
                    if (snapshot.key == "latitude") {
                        tmp.point.latitude = snapshot.value.toString().toDouble()
                    }
                    if (snapshot.key == "longitude") {
                        tmp.point.longitude = snapshot.value.toString().toDouble()

                    }
                    if (snapshot.key == "title") {
                        tmp.title = snapshot.value.toString()

                    }
                    if (snapshot.key == "text") {
                        tmp.text = snapshot.value.toString()
                    }
                    if (snapshot.key == "uid") {
                        tmp.uid = snapshot.value.toString()
                    }
                    if (snapshot.key == "uid") {
                        tmp.uid = snapshot.value.toString()
                    }
                    tmp.time = p0.key.toString()
                }
                App.INSTANCE.geoPoint.add(tmp)
                Log.d("ttttttt", App.INSTANCE.geoPoint.toString())
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val tmp = LocationModel(GeoPoint(0.0, 0.0), "", "", "", "")
                for (snapshot in p0.children) {
                    if (snapshot.key == "latitude") {
                        tmp.point.latitude = snapshot.value.toString().toDouble()
                    }
                    if (snapshot.key == "longitude") {
                        tmp.point.longitude = snapshot.value.toString().toDouble()

                    }
                    if (snapshot.key == "title") {
                        tmp.title = snapshot.value.toString()

                    }
                    if (snapshot.key == "text") {
                        tmp.text = snapshot.value.toString()
                    }
                    if (snapshot.key == "uid") {
                        tmp.uid = snapshot.value.toString()
                    }
                    if (snapshot.key == "uid") {
                        tmp.uid = snapshot.value.toString()
                    }
                    tmp.time = p0.key.toString()
                }
                App.INSTANCE.geoPoint.add(tmp)
//                tmp.point.distanceToAsDouble()
                Log.d("ttttttt", App.INSTANCE.geoPoint.toString())
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })


    }

}