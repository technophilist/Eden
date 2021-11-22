package com.example.eden.data.domain

import androidx.compose.ui.graphics.ImageBitmap

/**
 * A class that contains all the information related to
 * an incident.
 */
data class IncidentReportInfo(
    val name:String,
    val email:String,
    val address:String,
    val city:String,
    val state:String,
    val typeOfIncident:String,
    val description:String,
    val image:ImageBitmap
)
