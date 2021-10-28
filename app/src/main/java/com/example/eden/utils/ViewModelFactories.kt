package com.example.eden.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eden.auth.AuthenticationService
import com.example.eden.viewmodels.EdenLogInViewModel
import kotlinx.coroutines.CoroutineDispatcher

class LogInViewModelFactory(
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        EdenLogInViewModel(authenticationService, defaultDispatcher) as T
}