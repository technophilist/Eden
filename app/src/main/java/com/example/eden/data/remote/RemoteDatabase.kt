package com.example.eden.data.remote

import androidx.lifecycle.LiveData
import com.example.eden.data.domain.EdenUser
import com.example.eden.data.domain.IncidentReportInfo
import com.example.eden.data.domain.NotificationInfo
import com.example.eden.data.domain.PetInfo


/**
 * An interface that consists of all the fields and methods required
 * for a remote database.
 */
interface RemoteDatabase {
    val petsAvailableForAdoption: LiveData<List<PetInfo>>
    /**
     * Used to get a [LiveData] that contains a list of all
     * [NotificationInfo] objects related to the [currentUser].
     * Any updates to the underlying Firebase collection will
     * be automatically reflected in the Livedata, in real time.
     */
    fun listenForNotifications(currentUser: EdenUser): LiveData<List<NotificationInfo>>
    /**
     * Used when a user with [userId] wants to send a request for
     * adopting a pet with [petId].
     */
    suspend fun sendRequestForAdoption(userId: String, petId: String)
    /**
     * Used to save an incident report with the provided [userId],
     * [email] and [IncidentReportInfo].
     */
    suspend fun saveIncidentReport(userId: String, email: String, reportInfo: IncidentReportInfo)
}