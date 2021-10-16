package com.example.eden.viewmodels

import androidx.lifecycle.ViewModel
import com.example.eden.R
import com.example.eden.data.domain.PetInfo

interface HomeScreenViewModel {
    val featuredList: List<PetInfo>
    val recommendedList: List<PetInfo>
    fun addPetToFavourites(petInfo: PetInfo)
    fun filterRecommendedList(filterOptions: FilterOptions): List<PetInfo>
    enum class FilterOptions { DOGS, CATS }
}

class EdenHomeScreenViewModel : ViewModel(), HomeScreenViewModel {
    override val featuredList: List<PetInfo>
        get() = List(10) { PetInfo("Cherry", "ds", "Dog", "Pug", 25_000f, R.drawable.placeholder) }

    override val recommendedList: List<PetInfo>
        get() = emptyList()

    override fun addPetToFavourites(petInfo: PetInfo) {
        TODO("Not yet implemented")
    }

    override fun filterRecommendedList(filterOptions: HomeScreenViewModel.FilterOptions): List<PetInfo> {
        TODO("Not yet implemented")
    }
}