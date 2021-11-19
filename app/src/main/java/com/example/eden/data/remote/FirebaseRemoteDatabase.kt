package com.example.eden.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eden.data.domain.EdenUser
import com.example.eden.data.domain.NotificationInfo
import com.example.eden.data.domain.PetInfo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class FirebaseRemoteDatabase : RemoteDatabase {
    private val fireStore = Firebase.firestore
    private val _petsAvailableForAdoption = MutableLiveData(emptyList<PetInfo>())
    override val petsAvailableForAdoption = _petsAvailableForAdoption as LiveData<List<PetInfo>>

    init {
        listenForRealtimeUpdates()
    }

    private fun listenForRealtimeUpdates() {
        fireStore.collection(PETS_AVAILABLE_FOR_ADOPTION_COLLECTION_PATH)
            .addSnapshotListener { snapshot, exception ->
                if (exception == null) {
                    val list = snapshot!!.documents.map(DocumentSnapshot::toPetInfo)
                    _petsAvailableForAdoption.value = list
                }
            }
    }

    /**
     * Used when a user with [userId] wants to send a request for
     * adopting a pet with [petId].
     *
     * Calling this function will move the pet document located
     * in the [PETS_AVAILABLE_FOR_ADOPTION_COLLECTION_PATH] collection
     * to the [PETS_REQUESTED_FOR_ADOPTION_COLLECTION_PATH] collection.
     */
    override suspend fun sendRequestForAdoption(userId: String, petId: String) {
        runCatching {
            // get the pet document from "petsAvailableForAdoption" collection"
            val petDocument = fireStore.collection(PETS_AVAILABLE_FOR_ADOPTION_COLLECTION_PATH)
                .whereEqualTo("id", petId)
                .get()
                .await()
                .documents
                .first()
            // add the document to the "requestedForAdoption" collection
            petDocument.data?.let {
                fireStore.collection(PETS_REQUESTED_FOR_ADOPTION_COLLECTION_PATH)
                    .add(it)
                    .await()
            }
            // delete the document from "petsAvailableForAdoption" collection
            fireStore.document("$PETS_AVAILABLE_FOR_ADOPTION_COLLECTION_PATH/${petDocument.id}")
                .delete()
                .await()
        }.getOrElse { Timber.w("${it.cause}: Failed to send adoption request.") }
    }

    override fun listenForNotifications(currentUser: EdenUser): LiveData<List<NotificationInfo>> {
        val liveData = MutableLiveData(emptyList<NotificationInfo>())
        fireStore.collection(RECENTLY_SENT_NOTIFICATIONS_COLLECTION_PATH)
            .whereEqualTo("uid", currentUser.id)
            .addSnapshotListener { snapshot, exception ->
                if (exception == null) {
                    liveData.value = snapshot!!.documents.map(DocumentSnapshot::toNotificationInfo)
                }
            }
        return liveData
    }


    companion object {
        private const val PETS_AVAILABLE_FOR_ADOPTION_COLLECTION_PATH =
            "mobile/pet-adoption/availableForAdoption"
        private const val PETS_REQUESTED_FOR_ADOPTION_COLLECTION_PATH =
            "mobile/pet-adoption/requestedForAdoption"
        private const val RECENTLY_SENT_NOTIFICATIONS_COLLECTION_PATH = "mobile/notifications/recentlySent"
    }
}