package com.example.eden.auth

import android.net.Uri
import com.google.firebase.auth.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await

/**
 * TODO()
 * This extension method is used for creating a [FirebaseUser] with
 * [name],[email],[password] and profile picture ([profilePhotoUri]).
 *
 * Firebase doesn't provide a default method to create a user along with a
 * display name and profile photo.In order to perform such a task we need to
 * chain two methods - [FirebaseAuth.createUserWithEmailAndPassword] and
 * [FirebaseAuth.updateCurrentUser].

 * @throws  FirebaseAuthWeakPasswordException  thrown if the password is not strong enough
 * @throws FirebaseAuthInvalidCredentialsException thrown if the email address is malformed
 * @throws FirebaseAuthUserCollisionException thrown if there already exists an account with the given email address
 * @throws FirebaseAuthInvalidUserException thrown if the current user's account has been disabled, deleted, or its credentials are no longer valid.
 */
suspend fun FirebaseAuth.createUser(
    name: String,
    email: String,
    password: String,
    profilePhotoUri: Uri?
): FirebaseUser = runCatching {
    createUserWithEmailAndPassword(email, password).await()
    //if user is created successfully, set the display name and profile picture
    val userProfileChangeRequest = UserProfileChangeRequest.Builder()
        .setDisplayName(name)
        .setPhotoUri(profilePhotoUri)
        .build()
    currentUser!!.updateProfile(userProfileChangeRequest).await()
    currentUser!!
}.getOrThrow()

