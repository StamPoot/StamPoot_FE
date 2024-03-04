package com.example.footstamp.data.util

import com.example.footstamp.R

enum class SeoulLocation(val location: String, val map: Int) {
    CENTRAL("중부", R.drawable.map_central),
    EAST("동부", R.drawable.map_east),
    WEST("서부", R.drawable.map_west),
    SOUTH("남부", R.drawable.map_south),
    NORTH("북부", R.drawable.map_north),
}