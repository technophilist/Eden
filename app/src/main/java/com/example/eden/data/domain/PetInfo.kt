package com.example.eden.data.domain

import androidx.annotation.DrawableRes

/**
 * This class is used to depict the entire profile of a pet.
 */
data class PetInfo(
    val id:Int,
    val name:String,
    val age:String,
    val color:String,
    val weight:String,
    val description:String,
    val type:String,
    val breed:String,
    val gender:String,
    val imageResource:String
)
