package com.example.eden.data.remote

import com.example.eden.data.domain.PetInfo

interface RemoteDatabase {
    suspend fun fetchAllPetsAvailableForAdoption(): List<PetInfo>
    suspend fun sendRequestForAdoption(userId: String, petId: String)
}