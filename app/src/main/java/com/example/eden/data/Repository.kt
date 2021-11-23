package com.example.eden.data

import androidx.lifecycle.LiveData
import com.example.eden.data.domain.EdenUser
import com.example.eden.data.domain.IncidentReportInfo
import com.example.eden.data.domain.NotificationInfo
import com.example.eden.data.domain.PetInfo
import com.example.eden.data.remote.RemoteDatabase

interface Repository {
    val petsAvailableForAdoption: LiveData<List<PetInfo>>
    fun listenForNotifications(currentUser: EdenUser): LiveData<List<NotificationInfo>>
    suspend fun sendRequestForAdoption(user: EdenUser, petInfo: PetInfo)
    suspend fun sendIncidentReport(user: EdenUser, reportInfo: IncidentReportInfo)
}

class EdenRepository(
    private val remoteDatabase: RemoteDatabase
) : Repository {
    override val petsAvailableForAdoption: LiveData<List<PetInfo>> =
        remoteDatabase.petsAvailableForAdoption

    override fun listenForNotifications(currentUser: EdenUser) =
        remoteDatabase.listenForNotifications(currentUser)

    override suspend fun sendRequestForAdoption(user: EdenUser, petInfo: PetInfo) {
        remoteDatabase.sendRequestForAdoption(user.id, petInfo.id)
    }

    override suspend fun sendIncidentReport(user: EdenUser, reportInfo: IncidentReportInfo) {
        if (user.email == null) throw IllegalStateException("Incident report not sent. User email is null.")
        else remoteDatabase.saveIncidentReport(user.id, user.email, reportInfo)
    }
}