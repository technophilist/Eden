package com.example.eden.di

import com.example.eden.auth.AuthenticationService
import com.example.eden.auth.FirebaseAuthenticationService

class AppContainer {

    val authenticationService = FirebaseAuthenticationService() as AuthenticationService

}