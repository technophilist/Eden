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
    data class Failure(val failureType: FailureType) : AuthenticationResult()

    /**
     * An enum consisting of all the different types of failures
     * related to [AuthenticationService]
     */
    enum class FailureType {
        /**
         * Indicates that an authentication failure occurred due to
         * an invalid email address.
         */
        InvalidEmail,

        /**
         * Indicates that an authentication failure occurred due to
         * an invalid password.
         */
        InvalidPassword,

        /**
         * Indicates that an authentication failure occurred as a
         * result of one or more of the credentials being invalid.
         */
        InvalidCredentials,

        /**
         * Indicates that an authentication failure occurred
         * as a result of an attempt to create an already existing
         * user with the same credentials.
         */
        UserCollision,

        /**
         * Indicates that an authentication failure occurred
         * during the creation of a new user account.
         */
        AccountCreation,

        /**
         * Indicates that an authentication failure occurred as
         * a result of an attempt made to fetch the details
         * of a non-existent user.
         */
        InvalidUser,

        /**
         * Indicates that a failure occurred due to a network
         * error.
         */
        NetworkFailure
    }
}
