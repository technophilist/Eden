package com.example.eden.data

import com.example.eden.data.domain.EdenUser
import com.example.eden.data.domain.PetInfo
import com.example.eden.data.remote.RemoteDatabase

interface Repository {
    suspend fun fetchAllPetsAvailableForAdoption(): List<PetInfo>
    suspend fun sendRequestForAdoption(user: EdenUser, petInfo: PetInfo)
}

class EdenRepository(
    private val remoteDatabase: RemoteDatabase
) : Repository {
    override suspend fun fetchAllPetsAvailableForAdoption(): List<PetInfo> =
        remoteDatabase.fetchAllPetsAvailableForAdoption()

    override suspend fun sendRequestForAdoption(user: EdenUser, petInfo: PetInfo) {
        remoteDatabase.sendRequestForAdoption(user.id, petInfo.id)
    }
}