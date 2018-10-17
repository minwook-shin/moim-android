package io.github.teammoim.moim.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.teammoim.moim.BuildConfig
import io.github.teammoim.moim.R
import io.github.teammoim.moim.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_ar.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.ItemizedIconOverlay
import androidx.core.content.ContextCompat
import io.github.teammoim.moim.App
import org.jetbrains.anko.longToast


class ARFragment : BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ar,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map.setTileSource(TileSourceFactory.MAPNIK)
        setupMapView()

        val locationOverlay = ItemizedIconOverlay(addPoint(), object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
            override fun onItemSingleTapUp(i: Int, overlayItem: OverlayItem): Boolean {
//                App.INSTANCE.longToast(overlayItem.title + " " + overlayItem.snippet)
                val bottomSheetDialogFragment = EventInformationFragment()
                bottomSheetDialogFragment.show(activity?.supportFragmentManager, bottomSheetDialogFragment.tag)
                return true
            }

            override fun onItemLongPress(i: Int, overlayItem: OverlayItem): Boolean {
                return false
            }
        }, activity!!.applicationContext)

        this.map.overlays.add(locationOverlay)
        map.overlays.add(locationOverlay)
    }

    private fun addPoint(): ArrayList<OverlayItem> {
        val overlayItemArrayList : ArrayList<OverlayItem> = ArrayList()

        val geoPoint = GeoPoint(37.6096409, 126.99769700000002)
        val overlayItem = OverlayItem("이벤트 이름", "이벤트 설명", geoPoint)
        val markerDrawable = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.ic_launcher_foreground)
        overlayItem.setMarker(markerDrawable)

        overlayItemArrayList.add(overlayItem)
        return overlayItemArrayList
    }

    private fun setupMapView() {
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        map.isClickable = true
        map.controller.setZoom(19.0)
        val startPoint = GeoPoint(37.6096409, 126.99769700000002)
        map.controller.setCenter(startPoint)
    }
}
