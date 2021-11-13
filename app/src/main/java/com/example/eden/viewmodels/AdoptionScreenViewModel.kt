package com.example.eden.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.eden.data.Repository
import com.example.eden.data.domain.PetInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AdoptionScreenViewModel {
    val featuredList: LiveData<List<PetInfo>>
    val recommendedList: LiveData<List<PetInfo>>
    val currentlyAppliedFilter: LiveData<FilterOptions>
    fun addPetToFavourites(petInfo: PetInfo)
    fun applyFilter(filterOptions: FilterOptions)
    enum class FilterOptions { ALL, DOGS, CATS }
}

class EdenAdoptionScreenViewModel(
    private val repository: Repository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), AdoptionScreenViewModel {
    private val petsAvailableForAdoption = repository.petsAvailableForAdoption

    private val _currentlyAppliedFilter = MutableLiveData(AdoptionScreenViewModel.FilterOptions.ALL)
    override val currentlyAppliedFilter =
        _currentlyAppliedFilter as LiveData<AdoptionScreenViewModel.FilterOptions>

    private val _featuredList = MutableLiveData(emptyList<PetInfo>())
    override val featuredList = _featuredList as LiveData<List<PetInfo>>

    private val _recommendedList = MutableLiveData(emptyList<PetInfo>())
    override val recommendedList = _recommendedList as LiveData<List<PetInfo>>

    private val petsAvailableForAdoptionObserver = Observer<List<PetInfo>> { newPetInfoList ->
        currentlyAppliedFilter.value?.let { filterOption ->
            val filteredList = applyFilterToList(filterOption, newPetInfoList)
            _featuredList.value = filteredList
            _recommendedList.value = filteredList
        }
    }

    init {
        petsAvailableForAdoption.observeForever(petsAvailableForAdoptionObserver)
    }

    /**
     * Applies the [selectedFilter] to the [list] and returns the
     * filtered list.
     */
    private fun applyFilterToList(
        selectedFilter: AdoptionScreenViewModel.FilterOptions,
        list: List<PetInfo>
    ): List<PetInfo> = when (selectedFilter) {
        AdoptionScreenViewModel.FilterOptions.ALL -> list
        AdoptionScreenViewModel.FilterOptions.DOGS -> list.filter { it.type == "Dog" }
        AdoptionScreenViewModel.FilterOptions.CATS -> list.filter { it.type == "Cat" }
    }

    override fun addPetToFavourites(petInfo: PetInfo) {
        TODO("Not yet implemented")
    }

    override fun applyFilter(filterOptions: AdoptionScreenViewModel.FilterOptions) {
        _currentlyAppliedFilter.value = filterOptions
        // filter list
        petsAvailableForAdoption.value?.let { petInfoList ->
            val filteredList = applyFilterToList(filterOptions, petInfoList)
            _featuredList.value = filteredList
            _recommendedList.value = filteredList
        }
    }

    override fun onCleared() {
        super.onCleared()
        petsAvailableForAdoption.removeObserver(petsAvailableForAdoptionObserver)

    }
}

