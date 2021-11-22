package com.example.eden.data.remote

import androidx.lifecycle.LiveData
import com.example.eden.data.domain.EdenUser
import com.example.eden.data.domain.IncidentReportInfo
import com.example.eden.data.domain.NotificationInfo
import com.example.eden.data.domain.PetInfo

interface RemoteDatabase {
    val petsAvailableForAdoption:LiveData<List<PetInfo>>
    fun listenForNotifications(currentUser:EdenUser):LiveData<List<NotificationInfo>>
    suspend fun sendRequestForAdoption(userId: String, petId: String)
    suspend fun saveIncidentReport(reportInfo: IncidentReportInfo)
}