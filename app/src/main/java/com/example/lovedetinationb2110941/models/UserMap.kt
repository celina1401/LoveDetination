package com.example.lovedetinationb2110941.models

import java.io.Serializable

data class UserMap(
    val title: String,
    val places: List<Place>
):Serializable
