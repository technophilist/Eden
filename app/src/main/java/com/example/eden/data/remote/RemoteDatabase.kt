package com.example.eden.data.remote

import androidx.lifecycle.LiveData
import com.example.eden.data.domain.PetInfo

interface RemoteDatabase {
    val petsAvailableForAdoption:LiveData<List<PetInfo>>
    suspend fun sendRequestForAdoption(userId: String, petId: String)
}