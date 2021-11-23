package com.example.eden.data.domain

import android.graphics.Bitmap
import com.example.eden.data.dto.IncidentReportInfoDTO

/**
 * A class that contains all the information related to
 * an incident.
 */
data class IncidentReportInfo(
    val address: String,
    val city: String,
    val state: String,
    val typeOfIncident: String,
    val description: String,
    val image: Bitmap
)

/**
 * Used to convert an instance of [IncidentReportInfo]domain object
 * to an instance of [IncidentReportInfoDTO] with the specified
 * [userID],[email] and [imageURLString]
 */
fun IncidentReportInfo.toIncidentReportInfoDTO(
    userID: String,
    email: String,
    imageURLString: String
) = IncidentReportInfoDTO(
    userID = userID,
    email = email,
    address = address,
    city = city,
    state = state,
    typeOfIncident = typeOfIncident,
    description = description,
    imageURL = imageURLString
)
