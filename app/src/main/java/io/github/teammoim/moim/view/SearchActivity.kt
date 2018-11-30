package io.github.teammoim.moim.view

import android.os.Bundle
import io.github.teammoim.moim.App
import io.github.teammoim.moim.BuildConfig
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseActivity
import io.github.teammoim.moim.common.FirebaseManager
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.OverlayItem
import java.util.*
import kotlin.math.floor

class SearchActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val overlayItemArrayList : ArrayList<OverlayItem> = ArrayList()

        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map.setTileSource(TileSourceFactory.MAPNIK)
        setupMapView()


        var receiver = object :MapEventsReceiver{
            override fun longPressHelper(p: GeoPoint?): Boolean {
                val cal = Calendar.getInstance()
                val geoText = cal.timeInMillis.toString()
                FirebaseManager.getRef("events")?.child(geoText)?.child("uid")?.setValue(FirebaseManager.getUserUid())
                FirebaseManager.getRef("events")?.child(geoText)?.child("title")?.setValue(titleText.text.toString())
                FirebaseManager.getRef("events")?.child(geoText)?.child("text")?.setValue(introText.text.toString())
                FirebaseManager.getRef("events")?.child(geoText)?.child("url")?.setValue("")

                FirebaseManager.getRef("events")?.child(geoText)?.child("latitude")?.setValue(p?.latitude.toString())
                FirebaseManager.getRef("events")?.child(geoText)?.child("longitude")?.setValue(p?.longitude.toString())

                titleText.setText("")
                introText.setText("")

                map.snackbar("이벤트가 정상적으로 등록되었습니다.")
                return true
            }

            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                map.snackbar("이벤트를 등록하려면 이 곳을 누르고 있으세요.")
                return false
            }

        }
        @Suppress("DEPRECATION") var overlayEvents = MapEventsOverlay(baseContext, receiver)
        map.overlays.add(overlayEvents)

        prevButton.setOnClickListener {
            this.onBackPressed()
        }
    }

    private fun setupMapView() {
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        map.isClickable = true
        map.controller.setZoom(19.0)
        val startPoint = GeoPoint(App.INSTANCE.myLatitude, App.INSTANCE.myLatitude)
        map.controller.setCenter(startPoint)
    }
}