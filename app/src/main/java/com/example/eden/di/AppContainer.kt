package com.example.eden.di

import com.example.eden.auth.AuthenticationService
import com.example.eden.auth.FirebaseAuthenticationService
import com.example.eden.data.EdenRepository
import com.example.eden.data.Repository
import com.example.eden.data.remote.FirebaseRemoteDatabase
import com.example.eden.utils.AdoptionScreenViewModelFactory
import com.example.eden.utils.LogInViewModelFactory
import com.example.eden.utils.SignUpViewModelFactory
import kotlinx.coroutines.Dispatchers

class AppContainer {
    private val defaultDispatcher = Dispatchers.IO
    private val authenticationService = FirebaseAuthenticationService() as AuthenticationService

    //repository
    private val remoteDatabase = FirebaseRemoteDatabase()
    private val repository = EdenRepository(remoteDatabase) as Repository

    // viewmodel factories
    val loginViewModelFactory = LogInViewModelFactory(authenticationService, defaultDispatcher)
    val signUpViewModelFactory = SignUpViewModelFactory(authenticationService, defaultDispatcher)
    val adoptionScreenViewModelFactory = AdoptionScreenViewModelFactory(repository, authenticationService, defaultDispatcher)
}