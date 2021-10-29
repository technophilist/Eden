package com.example.eden.di

import com.example.eden.auth.AuthenticationService
import com.example.eden.auth.FirebaseAuthenticationService
import com.example.eden.utils.LogInViewModelFactory
import kotlinx.coroutines.Dispatchers

class AppContainer {
    private val defaultDispatcher = Dispatchers.IO
    private val authenticationService = FirebaseAuthenticationService() as AuthenticationService
    val loginViewModelFactory = LogInViewModelFactory(authenticationService,defaultDispatcher)
}