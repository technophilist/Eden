package com.example.eden.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eden.auth.AuthenticationService
import com.example.eden.data.Repository
import com.example.eden.data.domain.PetInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * An interface that consists of all the fields and methods required
 * for an AdoptionScreenViewModel
 */
interface AdoptionScreenViewModel {
    val featuredList: State<List<PetInfo>>
    val recommendedList: State<List<PetInfo>>
    val currentlyAppliedFilter: State<FilterOptions>

    /**
     * Used to add the specified [petInfo] to favourites.
     */
    fun addPetToFavourites(petInfo: PetInfo)

    /**
     * Used to remove the specified [petInfo] to favourites.
     */
    fun removePetFromFavourites(petInfo: PetInfo)

    /**
     * Used to send a request for adopting a pet with the specified
     * [petInfo].
     */
    fun sendRequestForAdoption(petInfo: PetInfo)

    /**
     * Used to apply the specified [filterOption].
     * The filter will modify the [featuredList] and [recommendedList].
     */
    fun applyFilter(filterOption: FilterOptions)

    /**
     * An enum class that specifies all the different types of filters
     * that can be applied.
     */
    enum class FilterOptions { ALL, DOGS, CATS }
}

class EdenAdoptionScreenViewModel(
    private val repository: Repository,
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), AdoptionScreenViewModel {
    private val petsAvailableForAdoption = repository.petsAvailableForAdoption

    private val _currentlyAppliedFilter = mutableStateOf(AdoptionScreenViewModel.FilterOptions.ALL)
    override val currentlyAppliedFilter =
        _currentlyAppliedFilter as State<AdoptionScreenViewModel.FilterOptions>

    private val _featuredList = mutableStateOf(emptyList<PetInfo>())
    override val featuredList = _featuredList as State<List<PetInfo>>

    private val _recommendedList = mutableStateOf(emptyList<PetInfo>())
    override val recommendedList = _recommendedList as State<List<PetInfo>>

    private val petsAvailableForAdoptionObserver = Observer<List<PetInfo>> { newPetInfoList ->
        val filteredList = applyFilterToList(currentlyAppliedFilter.value, newPetInfoList)
        _featuredList.value = filteredList
        _recommendedList.value = filteredList
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

    override fun sendRequestForAdoption(petInfo: PetInfo) {
        authenticationService.currentUser?.let { currentUser ->
            viewModelScope.launch(defaultDispatcher) {
                repository.sendRequestForAdoption(currentUser, petInfo)
            }
        }
    }

    override fun addPetToFavourites(petInfo: PetInfo) {
        //TODO
    }

    override fun removePetFromFavourites(petInfo: PetInfo) {
        // TODO
    }

    override fun applyFilter(filterOption: AdoptionScreenViewModel.FilterOptions) {
        _currentlyAppliedFilter.value = filterOption
        // filter list
        petsAvailableForAdoption.value?.let { petInfoList ->
            val filteredList = applyFilterToList(filterOption, petInfoList)
            _featuredList.value = filteredList
            _recommendedList.value = filteredList
        }
    }

    override fun onCleared() {
        super.onCleared()
        petsAvailableForAdoption.removeObserver(petsAvailableForAdoptionObserver)

    }
}

