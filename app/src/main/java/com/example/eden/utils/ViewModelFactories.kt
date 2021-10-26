package com.example.eden.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eden.auth.AuthenticationService
import com.example.eden.viewmodels.EdenLogInViewModel

class LogInViewModelFactory(
    private val authenticationService: AuthenticationService,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = EdenLogInViewModel(authenticationService) as T
}