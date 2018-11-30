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


class ARFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map.setTileSource(TileSourceFactory.MAPNIK)
        setupMapView()

        val locationOverlay = ItemizedIconOverlay(addPoint(), object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
            override fun onItemSingleTapUp(i: Int, overlayItem: OverlayItem): Boolean {
                val bottomSheetDialogFragment = EventInformationFragment(overlayItem)
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
        val overlayItemArrayList: ArrayList<OverlayItem> = ArrayList()

        for (i in 0 until App.INSTANCE.geoPoint.size) {
            val geoPoint = App.INSTANCE.geoPoint[i].point
            val overlayItem = OverlayItem(App.INSTANCE.geoPoint[i].time, App.INSTANCE.geoPoint[i].title, App.INSTANCE.geoPoint[i].text, geoPoint)
            val markerDrawable = ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.marker_default)
            overlayItem.setMarker(markerDrawable)

            overlayItemArrayList.add(overlayItem)
        }
        return overlayItemArrayList
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
