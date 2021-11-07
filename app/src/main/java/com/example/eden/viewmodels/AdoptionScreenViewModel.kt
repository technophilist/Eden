package com.example.eden.viewmodels

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eden.data.Repository
import com.example.eden.data.domain.PetInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AdoptionScreenViewModel {
    val featuredList: LiveData<List<PetInfo>> 
    val recommendedList: LiveData<List<PetInfo>>
    fun addPetToFavourites(petInfo: PetInfo)
    fun applyFilter(filterOptions: FilterOptions)
    enum class FilterOptions { ALL, DOGS, CATS }
}

class EdenAdoptionScreenViewModel(
    private val repository: Repository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), AdoptionScreenViewModel {
    override val featuredList: LiveData<List<PetInfo>> = repository.petsAvailableForAdoption
    override val recommendedList: LiveData<List<PetInfo>> = repository.petsAvailableForAdoption // TODO
    override fun addPetToFavourites(petInfo: PetInfo) {
        TODO("Not yet implemented")
    }
    override fun applyFilter(filterOptions: AdoptionScreenViewModel.FilterOptions) {
        TODO("Not yet implemented")
    }
}

