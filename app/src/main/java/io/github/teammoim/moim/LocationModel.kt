package io.github.teammoim.moim

import org.osmdroid.util.GeoPoint

data class LocationModel(
        open var point : GeoPoint,
        var text : String,
        var title : String,
        var uid : String,
        var time : String
)