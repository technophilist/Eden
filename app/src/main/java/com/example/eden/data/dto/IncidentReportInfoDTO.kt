package com.example.eden.data.dto


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
