package com.example.eden.di

import com.example.eden.auth.AuthenticationService
import com.example.eden.auth.FirebaseAuthenticationService
import com.example.eden.data.EdenRepository
import com.example.eden.data.Repository
import com.example.eden.data.remote.FirebaseRemoteDatabase
import com.example.eden.utils.*
import kotlinx.coroutines.Dispatchers

class AppContainer {
    private val defaultDispatcher = Dispatchers.IO
    val authenticationService = FirebaseAuthenticationService() as AuthenticationService

    //repository
    private val remoteDatabase = FirebaseRemoteDatabase()
    private val repository = EdenRepository(remoteDatabase) as Repository

    // viewmodel factories TODO Fix - All viewModel factories will be kept in memory until the application is killed.
    val loginViewModelFactory = LogInViewModelFactory(authenticationService, defaultDispatcher)
    val signUpViewModelFactory = SignUpViewModelFactory(authenticationService, defaultDispatcher)
    val adoptionScreenViewModelFactory = AdoptionScreenViewModelFactory(repository, authenticationService, defaultDispatcher)
    val notificationScreenViewModelFactory = NotificationScreenViewModelFactory(repository,authenticationService)
    val reportScreenViewModelFactory = ReportsScreenViewModelFactory(repository,authenticationService,defaultDispatcher)
}