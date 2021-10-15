package com.example.eden.data.domain

import androidx.annotation.DrawableRes

data class PetInfo(
    val name:String,
    val description:String,
    val price:Float,
    @DrawableRes val imageResource:Int
)
