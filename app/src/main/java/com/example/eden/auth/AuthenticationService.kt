package com.example.eden.auth

import android.net.Uri
import com.example.eden.data.domain.EdenUser

interface AuthenticationService {
    /**
     * The current user represents the user that is
     * currently logged in. If it is null, it implies
     * that there is no logged in user.
     */
    val currentUser: EdenUser?
    suspend fun signIn(email: String, password: String): AuthenticationResult
    suspend fun createAccount(
        username: String,
        email: String,
        password: String,
        profilePhotoUri: Uri? = null
    ): AuthenticationResult
    fun signOut()
}

sealed class AuthenticationResult {
    data class Success(val user: EdenUser) : AuthenticationResult()
    data class Failure(val authServiceException: AuthenticationServiceException) : AuthenticationResult()
}
