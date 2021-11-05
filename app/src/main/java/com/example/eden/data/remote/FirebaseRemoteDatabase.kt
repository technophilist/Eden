package com.example.eden.data.remote

import com.example.eden.data.domain.PetInfo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRemoteDatabase : RemoteDatabase {
    private val fireStore = Firebase.firestore

    override suspend fun fetchAllPetsAvailableForAdoption(): List<PetInfo> =
        fireStore.collection("mobile/pet-adoption/availableForAdoption")
            .get()
            .await()
            .documents
            .map(DocumentSnapshot::toPetInfo)

    /**
     * Used when a user with [userId] wants to send a request for
     * adopting a pet with [petId].
     */
    override suspend fun sendRequestForAdoption(userId: String, petId: String) {
        TODO("Not yet implemented")
    }
}