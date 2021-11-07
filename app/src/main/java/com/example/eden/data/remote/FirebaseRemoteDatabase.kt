package com.example.eden.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eden.data.domain.PetInfo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRemoteDatabase : RemoteDatabase {
    private val fireStore = Firebase.firestore
    private val _petsAvailableForAdoption = MutableLiveData(emptyList<PetInfo>())
    override val petsAvailableForAdoption = _petsAvailableForAdoption as LiveData<List<PetInfo>>

    init {
        listenForRealtimeUpdates()
    }

    private fun listenForRealtimeUpdates() {
        fireStore.collection("mobile/pet-adoption/availableForAdoption")
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
     */
    override suspend fun sendRequestForAdoption(userId: String, petId: String) {
        TODO("Not yet implemented")
    }
}