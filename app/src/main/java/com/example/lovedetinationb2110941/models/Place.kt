package com.example.lovedetinationb2110941.models

import java.io.Serializable

data class Place(
    val tile: String,
    val description: String,
    val latitude: Double, //Vĩ độ
    val longitude: Double // Kinh độ
):Serializable
