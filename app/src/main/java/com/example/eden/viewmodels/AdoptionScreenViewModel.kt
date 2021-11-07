package com.example.eden.viewmodels

import androidx.lifecycle.ViewModel
import com.example.eden.data.Repository
import com.example.eden.data.domain.PetInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AdoptionScreenViewModel {
    val featuredList: List<PetInfo> // change to live data
    val recommendedList: List<PetInfo> // change to live data
    fun addPetToFavourites(petInfo: PetInfo)
    fun applyFilter(filterOptions: FilterOptions)
    enum class FilterOptions { ALL, DOGS, CATS }
}

class EdenAdoptionScreenViewModel(
    private val repository: Repository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), AdoptionScreenViewModel {
    override val featuredList: List<PetInfo>
        get() = emptyList()

    override val recommendedList: List<PetInfo>
        get() = emptyList()

    override fun addPetToFavourites(petInfo: PetInfo) {
        TODO("Not yet implemented")
    }

    override fun applyFilter(filterOptions: AdoptionScreenViewModel.FilterOptions) {
        TODO("Not yet implemented")
    }
}

