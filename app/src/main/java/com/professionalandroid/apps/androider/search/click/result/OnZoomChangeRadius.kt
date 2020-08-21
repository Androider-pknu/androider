package com.professionalandroid.apps.androider.search.click.result

interface OnZoomChangeRadius {
    companion object{
        fun changeRadius(cameraZoom: Float): Double{
            val mapRadius: Double
            when(cameraZoom){
                in 20.0f .. 21.0f -> { // 23m
                    mapRadius = 0.023
                }
                in 19.0f .. 20.0f -> { // 47m
                    mapRadius = 0.047
                }
                in 18.0f .. 19.0f -> { // 94m
                    mapRadius = 0.094
                }
                in 17.0f .. 18.0f -> { // 188M
                    mapRadius = 0.188
                }
                in 16.0f .. 17.0f -> { // 375M
                    mapRadius = 0.375
                }
                in 15.0f .. 16.0f -> { // 750M
                    mapRadius = 0.750
                }
                in 14.0f .. 15.0f -> { // 1500M
                    mapRadius = 1.500
                }
                in 13.0f .. 14.0f -> { //3000M
                    mapRadius = 3.000
                }
                else -> { // 5KM
                    mapRadius = 5.000
                }
            }
            return mapRadius
        }
    }
}