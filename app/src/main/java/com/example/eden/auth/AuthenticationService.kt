package com.example.eden.auth

import android.net.Uri
import com.example.eden.auth.AuthenticationResult.*
import com.example.eden.data.domain.EdenUser

/**
 * An interface that contains the requisite fields and methods required
 * for an authentication service.
 */
interface AuthenticationService {
    /**
     * The current user represents the user that is
     * currently logged in. If it is null, it implies
     * that there is no logged in user.
     */
    val currentUser: EdenUser?

    /***
     * Used to sign in a user with the provided [email]
     * and [password]. An instance of [AuthenticationResult] will be
     * returned to indicate a successful or failed sign-in attempt.
     */
    suspend fun signIn(email: String, password: String): AuthenticationResult

    /**
     * Used to create a new user account with the provided [username],
     * [email],[password] and an optional [profilePhotoUri].
     * An instance of [AuthenticationResult] will be returned to
     * indicate whether an account was successfully created or not.
     */
    suspend fun createAccount(
        username: String,
        email: String,
        password: String,
        profilePhotoUri: Uri? = null
    ): AuthenticationResult

    /**
     * Used to sign out the current user.
     */
    fun signOut()
}

/**
 * A sealed class that encapsulates the status of initiating the
 * authentication process.
 *
 * This sealed class consist of two data classes representing
 * success and failure states.
 * The [Success] class contains an [EdenUser] object, which
 * represents the user who was successfully authenticated.
 * The [Failure] class contains the [FailureType] which can
 * be used to infer the type of failure.
 */
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
