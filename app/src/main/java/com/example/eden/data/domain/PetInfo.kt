package com.example.eden.data.domain

import androidx.annotation.DrawableRes

/**
 * This class is used to depict the entire profile of a pet.
 */
data class PetInfo(
    val id:Int,
    val name:String,
    val description:String,
    val type:String,
    val breed:String,
    val price:Float,
    @DrawableRes val imageResource:Int
)
