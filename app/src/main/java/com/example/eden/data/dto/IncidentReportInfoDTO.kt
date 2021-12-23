package com.example.eden.data.dto

/**
 * A DTO(Data Transfer Object) representing an Incident Report.
 */
data class IncidentReportInfoDTO(
    val userID: String,
    val email: String,
    val address: String,
    val city: String,
    val state: String,
    val typeOfIncident: String,
    val description: String,
    val imageURL: String
)
