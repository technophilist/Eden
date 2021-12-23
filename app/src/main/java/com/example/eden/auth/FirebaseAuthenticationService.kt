package com.example.eden.auth

import android.net.Uri
import com.example.eden.data.domain.EdenUser
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await

/**
 * A concrete implementation of [AuthenticationService] that makes use
 * of Firebase.
 */
class FirebaseAuthenticationService : AuthenticationService {

    private val firebaseAuth = FirebaseAuth.getInstance()
    override val currentUser get() = firebaseAuth.currentUser?.toEdenUser()

    /**
     * Used to signIn an existing user with the specified [email] and
     * [password].
     * @return an instance of [AuthenticationResult.Failure] if an error
     * occurred, or, an instance of [AuthenticationResult.Success] if
     * an account was created successfully.
     */
    override suspend fun signIn(
        email: String,
        password: String
    ): AuthenticationResult = runCatching {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        AuthenticationResult.Success(firebaseAuth.currentUser!!.toEdenUser())
    }.getOrElse {
        AuthenticationResult.Failure(
            when (it) {
                is FirebaseAuthInvalidUserException -> AuthenticationResult.FailureType.InvalidEmail
                is FirebaseAuthInvalidCredentialsException -> AuthenticationResult.FailureType.InvalidPassword
                else -> AuthenticationResult.FailureType.NetworkFailure
            }
        )
    }

    /**
     * Used to create a new user account with the specified [username],
     * [email],[password] and [profilePhotoUri].
     * @return an instance of [AuthenticationResult.Failure] if an error
     * occurred, or, an instance of [AuthenticationResult.Success] if
     * an account was created successfully.
     */
    override suspend fun createAccount(
        username: String,
        email: String,
        password: String,
        profilePhotoUri: Uri?
    ): AuthenticationResult = runCatching {
        val firebaseUser = firebaseAuth.createUser(username, email, password, profilePhotoUri)
        AuthenticationResult.Success(firebaseUser.toEdenUser())
    }.getOrElse {
        AuthenticationResult.Failure(
            when (it) {
                is FirebaseAuthWeakPasswordException -> AuthenticationResult.FailureType.InvalidPassword
                is FirebaseAuthInvalidCredentialsException -> AuthenticationResult.FailureType.InvalidCredentials
                is FirebaseAuthUserCollisionException -> AuthenticationResult.FailureType.UserCollision
                is FirebaseAuthInvalidUserException -> AuthenticationResult.FailureType.InvalidUser
                else -> AuthenticationResult.FailureType.NetworkFailure
            }
        )
    }

    /**
     * This method is used for signing-out the current signed-in user.
     */
    override fun signOut() {
        firebaseAuth.signOut()
    }

    /**
     * Utility method to convert an instance of [FirebaseUser] to
     * [EdenUser]
     */
    private fun FirebaseUser.toEdenUser() = EdenUser(uid, displayName, email, photoUrl)

}
