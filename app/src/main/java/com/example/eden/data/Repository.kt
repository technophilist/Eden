package com.example.eden.data

import androidx.lifecycle.LiveData
import com.example.eden.data.domain.EdenUser
import com.example.eden.data.domain.PetInfo
import com.example.eden.data.remote.RemoteDatabase

interface Repository {
    val petsAvailableForAdoption: LiveData<List<PetInfo>>
    suspend fun sendRequestForAdoption(user: EdenUser, petInfo: PetInfo)
}

class EdenRepository(
    private val remoteDatabase: RemoteDatabase
) : Repository {
    override val petsAvailableForAdoption:LiveData<List<PetInfo>> = remoteDatabase.petsAvailableForAdoption
    override suspend fun sendRequestForAdoption(user: EdenUser, petInfo: PetInfo) {
        remoteDatabase.sendRequestForAdoption(user.id, petInfo.id)
    }
}